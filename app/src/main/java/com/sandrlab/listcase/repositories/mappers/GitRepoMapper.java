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
package com.sandrlab.listcase.repositories.mappers;

import android.support.annotation.NonNull;

import com.sandrlab.listcase.data.network.response.GitHubRepoResponse;
import com.sandrlab.listcase.models.GitRepoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public final class GitRepoMapper {

    public static List<GitRepoModel> convertToGitRepoModelList(@NonNull GitHubRepoResponse response) {
        if (response.getItems() != null) {
            List<GitRepoModel> result = new ArrayList<>(response.getItems().size());
            for (GitHubRepoResponse.GitRepo gitRepo: response.getItems()) {
                result.add(convertToGitRepoModel(gitRepo));
            }
            return result;
        }

        return Collections.emptyList();
    }

    private static GitRepoModel convertToGitRepoModel(@NonNull GitHubRepoResponse.GitRepo gitRepo) {
        return new GitRepoModel(
                convertNullSafe(gitRepo.getName()),
                convertNullSafe(gitRepo.getFullName()),
                convertNullSafe(gitRepo.getDescription()),
                (gitRepo.getOwner() != null) ? convertNullSafe(gitRepo.getOwner().getAvatarUrl()) : "",
                convertNullSafe(gitRepo.getHtmlUrl()),
                (gitRepo.getOwner() != null) ? convertNullSafe(gitRepo.getOwner().getLogin()) : "",
                gitRepo.getStarGazersCount(),
                gitRepo.getWatchersCount()
        );
    }

    private static String convertNullSafe(String value) {
        return  (value == null) ? "" : value;
    }
}
