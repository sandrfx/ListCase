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
package com.sandrlab.listcase.repositories;

import android.support.annotation.NonNull;

import com.sandrlab.listcase.data.network.GitHubApi;
import com.sandrlab.listcase.models.GitRepoModel;
import com.sandrlab.listcase.repositories.mappers.GitRepoMapper;

import java.util.List;

import io.reactivex.Single;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public class GitReposRepositoryImpl implements GitReposRepository {

    private final GitHubApi gitHubApi;

    public GitReposRepositoryImpl(@NonNull GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }


    @Override
    public Single<List<GitRepoModel>> getTopAndroidRepos(int page) {
        return gitHubApi.getTopAndroidRepos(page)
                .map(GitRepoMapper::convertToGitRepoModelList)
                .singleOrError();
    }
}
