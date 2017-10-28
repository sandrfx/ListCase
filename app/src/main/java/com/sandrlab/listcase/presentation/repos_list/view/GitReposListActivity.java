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
package com.sandrlab.listcase.presentation.repos_list.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.sandrlab.listcase.R;
import com.sandrlab.listcase.di.ComponentManager;
import com.sandrlab.listcase.models.GitRepoModel;
import com.sandrlab.listcase.presentation.repos_list.presenter.GitReposListPresenter;
import com.sandrlab.listcase.presentation.repos_list.view.adapters.ReposListAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public class GitReposListActivity extends MvpAppCompatActivity implements GitReposListView {

    @Inject
    @InjectPresenter
    GitReposListPresenter gitReposListPresenter;

    private View progressBar;
    private RecyclerView reposList;
    private ReposListAdapter reposListAdapter;
    private LinearLayoutManager layoutManager;

    @ProvidePresenter
    GitReposListPresenter providePresenter() {
        return gitReposListPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ComponentManager.getInstance().getReposListComponent().inject(this);
        super.onCreate(savedInstanceState);
        initUI();
        gitReposListPresenter.subscribeForDataUpdate(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        gitReposListPresenter.unsubscribeFromDataUpdate();
        if (isFinishing()) {
            ComponentManager.getInstance().cleatReposListComponent();
        }
        super.onDestroy();
    }

    private void initUI() {
        setContentView(R.layout.ac_top_android_repos_list);
        progressBar = findViewById(R.id.progress_bar);
        reposList = (RecyclerView) findViewById(R.id.repos_list);
        reposListAdapter = new ReposListAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        reposList.setLayoutManager(layoutManager);
        reposList.setAdapter(reposListAdapter);
        setUpOnScrollListener();
    }

    @Override
    public void addTopAndroidReposListItems(@NonNull List<GitRepoModel> gitRepoModelsToAdd) {
        reposList.setVisibility(View.VISIBLE);
        reposListAdapter.addData(gitRepoModelsToAdd);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@StringRes int stringResId) {
        Snackbar.make(reposList, stringResId, Snackbar.LENGTH_SHORT).show();
    }

    private void setUpOnScrollListener() {
        reposList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                gitReposListPresenter.onScrolled(
                        layoutManager.getItemCount(),
                        layoutManager.findLastVisibleItemPosition()
                );
            }
        });
    }
}