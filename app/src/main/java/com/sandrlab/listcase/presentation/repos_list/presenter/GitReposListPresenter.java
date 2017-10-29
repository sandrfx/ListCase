/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sandrlab.listcase.presentation.repos_list.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sandrlab.listcase.R;
import com.sandrlab.listcase.domain.repos_list.GitReposListInteractor;
import com.sandrlab.listcase.presentation.repos_list.view.GitReposListView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
@InjectViewState
public class GitReposListPresenter extends MvpPresenter<GitReposListView> {

    private final static int SCROLL_THRESHOLD = 2;

    private volatile boolean loading;
    private volatile int pageNumber = 1;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PublishProcessor<Integer> scrollProcessor = PublishProcessor.create();

    private final GitReposListInteractor gitReposListInteractor;

    public GitReposListPresenter(@NonNull GitReposListInteractor gitReposListInteractor) {
        this.gitReposListInteractor = gitReposListInteractor;
    }

    public synchronized void onScrolled(int count, int lastVisibleItemIndex) {
        if (!loading && count <= (lastVisibleItemIndex + SCROLL_THRESHOLD)) {
            pageNumber++;
            scrollProcessor.onNext(pageNumber);
            loading = true;
        }
    }

    public void subscribeForDataUpdate(boolean forceImmediateUpdate) {
        Disposable disposable = scrollProcessor
                .onBackpressureDrop()
                .concatMap(page -> {
                    loading = true;
                    getViewState().disableSwipeToRefresh();
                    getViewState().showLoading();
                    return gitReposListInteractor.getTopAndroidRepos(page)
                            .subscribeOn(Schedulers.io())
                            .toFlowable();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repos -> {
                    getViewState().addTopAndroidReposListItems(repos);
                    loading = false;
                    getViewState().hideLoading();
                    getViewState().enableSwipeToRefresh();
                }, throwable -> getViewState().showMessage(R.string.error_loading_repositories));
        compositeDisposable.add(disposable);

        if (forceImmediateUpdate) {
            scrollProcessor.onNext(pageNumber);
        }
    }

    public void unsubscribeFromDataUpdate() {
        compositeDisposable.clear();
    }

    public void onRefreshTriggered() {
        unsubscribeFromDataUpdate();
        loading = false;
        final int FIRST_PAGE = 1;
        Disposable disposable = gitReposListInteractor.getTopAndroidRepos(FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repos -> {
                    getViewState().refreshListItems(repos);
                    getViewState().hideSwipeToRefreshLoading();
                    getViewState().showMessage(R.string.msg_refreshed);
                    pageNumber = 1;
                    subscribeForDataUpdate(false);
                }, throwable -> {
                    getViewState().showMessage(R.string.error_loading_repositories);
                    getViewState().hideSwipeToRefreshLoading();
                    subscribeForDataUpdate(false);
                });
        compositeDisposable.add(disposable);
    }
}
