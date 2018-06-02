package ru.geekbrains.android3_4.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.presenter.IReposPresenter;


/**
 * Created by Roman Syrchin on 6/2/18.
 */
class ReposRVAdapter extends Adapter<ReposRVAdapter.RepoCardViewHolder> {

    private IReposPresenter reposPresenter;

    public ReposRVAdapter(IReposPresenter reposPresenter) {
        this.reposPresenter = reposPresenter;
    }

    @NonNull
    @Override
    public RepoCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_repo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoCardViewHolder holder, int position) {
        reposPresenter.representCardView(holder, position);
    }

    @Override
    public int getItemCount() {
        return reposPresenter.getReposCount();
    }

    class RepoCardViewHolder extends RecyclerView.ViewHolder implements RepoCardView {

        @BindView(R.id.tv_repo_name)
        TextView repoNameTextView;

        @BindView(R.id.tv_ic_star)
        TextView repoIconStar;

        @BindView(R.id.tv_ic_private)
        TextView repoIconPrivate;

        @BindView(R.id.tv_ic_forked)
        TextView repoIconFork;

        public RepoCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setRepoName(String text) {
            repoNameTextView.setText(text);
        }

        @Override
        public void setFork() {
            repoIconFork.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_fork,  0);
        }

        @Override
        public void setPrivate() {
            repoIconPrivate.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_private, 0);
        }

        @Override
        public void setStarred() {
            repoIconStar.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_star, 0);
        }
    }
}
