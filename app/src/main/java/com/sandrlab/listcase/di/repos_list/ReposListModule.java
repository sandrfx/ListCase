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
package com.sandrlab.listcase.di.repos_list;

import android.support.annotation.NonNull;

import com.sandrlab.listcase.domain.repos_list.GitReposListInteractor;
import com.sandrlab.listcase.domain.repos_list.GitReposListInteractorImpl;
import com.sandrlab.listcase.presentation.repos_list.presenter.GitReposListPresenter;
import com.sandrlab.listcase.repositories.GitReposRepository;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
@Module
public class ReposListModule {

    @Provides
    @NonNull
    @ReposListScope
    GitReposListInteractor provideGitReposInteractor(@NonNull GitReposRepository gitReposRepository) {
        return new GitReposListInteractorImpl(gitReposRepository);
    }

    @Provides
    @NonNull
    @ReposListScope
    GitReposListPresenter provideTopAndroidReposPresenter(@NonNull GitReposListInteractor gitReposListInteractor,
                                                          @NonNull Router router) {
        return new GitReposListPresenter(gitReposListInteractor, router);
    }
}
