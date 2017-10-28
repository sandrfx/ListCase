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

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.sandrlab.listcase.models.GitRepoModel;

import java.util.List;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public interface GitReposListView extends MvpView {

    void addTopAndroidReposListItems(@NonNull List<GitRepoModel> gitRepoModelsToAdd);
    void showLoading();
    void hideLoading();
    void showMessage(@StringRes int stringResId);
}
