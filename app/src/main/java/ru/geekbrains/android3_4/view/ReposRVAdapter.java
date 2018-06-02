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
class ReposRVAdapter extends Adapter<ReposRVAdapter.ViewHolder> {

    private IReposPresenter reposPresenter;

    public ReposRVAdapter(IReposPresenter reposPresenter) {
        this.reposPresenter = reposPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_repo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setRepoNameTextView(reposPresenter.getRepoName(position));
    }

    @Override
    public int getItemCount() {
        return reposPresenter.getReposCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name)
        TextView repoNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setRepoNameTextView(String text) { repoNameTextView.setText(text); }
    }
}
