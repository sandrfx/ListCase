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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.sandrlab.listcase.R;
import com.sandrlab.listcase.di.ComponentManager;
import com.sandrlab.listcase.models.GitRepoModel;
import com.sandrlab.listcase.presentation.navigation.NavigationRouts;
import com.sandrlab.listcase.presentation.repos_list.presenter.GitReposListPresenter;
import com.sandrlab.listcase.presentation.repos_list.view.adapters.ReposListAdapter;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.commands.Forward;
import timber.log.Timber;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public class GitReposListActivity extends MvpAppCompatActivity implements GitReposListView {

    @Inject
    @InjectPresenter
    GitReposListPresenter gitReposListPresenter;

    @Inject
    NavigatorHolder navigatorHolder;

    private View progressBar;
    private RecyclerView reposList;
    private ReposListAdapter reposListAdapter;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @ProvidePresenter
    GitReposListPresenter providePresenter() {
        return gitReposListPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ComponentManager.getInstance().getReposListComponent().inject(this);
        super.onCreate(savedInstanceState);
        initUI();
        gitReposListPresenter.subscribeForDataUpdate(savedInstanceState == null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
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
        reposListAdapter = new ReposListAdapter(this, gitReposListPresenter);
        layoutManager = new LinearLayoutManager(this);
        reposList.setLayoutManager(layoutManager);
        reposList.setAdapter(reposListAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> gitReposListPresenter.onRefreshTriggered());

        setUpOnScrollListener();
    }

    @Override
    public void addTopAndroidReposListItems(@NonNull List<GitRepoModel> gitRepoModelsToAdd) {
        reposList.setVisibility(View.VISIBLE);
        reposListAdapter.addData(gitRepoModelsToAdd);
    }

    @Override
    public void refreshListItems(@NonNull List<GitRepoModel> newGitRepoModels) {
        reposList.setVisibility(View.VISIBLE);
        reposListAdapter.refreshData(newGitRepoModels);
    }

    @Override
    public void enableSwipeToRefresh() {
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void disableSwipeToRefresh() {
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void hideSwipeToRefreshLoading() {
        swipeRefreshLayout.setRefreshing(false);
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

    private Navigator navigator = command -> {
        if (Forward.class.isInstance(command)) {
            Forward forwardCommand = ((Forward) command);
            if (NavigationRouts.REPO_DETAILS.equals(forwardCommand.getScreenKey())) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(forwardCommand.getTransitionData().toString()));
                try {
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Timber.e(e.getMessage());
                }
            }
        }
    };
}
