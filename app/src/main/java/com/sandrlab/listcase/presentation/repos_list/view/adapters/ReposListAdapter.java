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
package com.sandrlab.listcase.presentation.repos_list.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandrlab.listcase.R;
import com.sandrlab.listcase.models.GitRepoModel;
import com.sandrlab.listcase.presentation.repos_list.presenter.GitReposListPresenter;
import com.sandrlab.listcase.presentation.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public class ReposListAdapter extends RecyclerView.Adapter<ReposListAdapter.ViewHolder> {

    private final Context context;
    private final GitReposListPresenter presenter;
    private final List<GitRepoModel> gitRepoModels = new ArrayList<>();

    public ReposListAdapter(@NonNull Context context, @NonNull GitReposListPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void addData(@NonNull List<GitRepoModel> newData) {
        gitRepoModels.addAll(newData);
        notifyDataSetChanged();
    }

    public void refreshData(@NonNull List<GitRepoModel> newData) {
        gitRepoModels.clear();
        gitRepoModels.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_repo_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GitRepoModel model = gitRepoModels.get(position);
        holder.repoName.setText(model.getName());
        holder.ownerName.setText(model.getOwner());
        ImageUtils.displayImageFromURL(context, model.getAvatarUrl(), holder.ownerImage);
        holder.clickableArea.setOnClickListener(view -> presenter.onRepoListItemClick(model));
    }

    @Override
    public int getItemCount() {
        return gitRepoModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View clickableArea;
        TextView repoName;
        TextView ownerName;
        ImageView ownerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            clickableArea = itemView.findViewById(R.id.clickable_area);
            repoName = itemView.findViewById(R.id.repo_name);
            ownerName = itemView.findViewById(R.id.owner_name);
            ownerImage = itemView.findViewById(R.id.owner_image);
        }
    }
}
