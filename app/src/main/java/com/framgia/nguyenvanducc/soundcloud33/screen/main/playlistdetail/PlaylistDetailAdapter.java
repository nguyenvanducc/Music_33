package com.framgia.nguyenvanducc.soundcloud33.screen.main.playlistdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.nguyenvanducc.soundcloud33.R;
import com.framgia.nguyenvanducc.soundcloud33.data.model.Track;
import com.framgia.nguyenvanducc.soundcloud33.screen.BaseRecyclerViewAdapter;

import java.util.List;

public class PlaylistDetailAdapter extends
        BaseRecyclerViewAdapter<PlaylistDetailAdapter.ViewHolder> {
    private List<Track> mTracks;
    private TrackClickListener mTrackClickListener;

    PlaylistDetailAdapter(Context context, List<Track> tracks, TrackClickListener listener) {
        super(context);
        this.mTracks = tracks;
        this.mTrackClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_track, viewGroup, false);
        return new ViewHolder(view, mTrackClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(mTracks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    public void removeTrack(Track track) {
        int index = mTracks.indexOf(track);
        mTracks.remove(track);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, mTracks.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageArtwork;
        private TextView mTextTitle;
        private TextView mTextArtist;
        private ImageView mImageFavorite;
        private ImageView mImageMore;
        private Track mTrack;
        private TrackClickListener mListener;

        ViewHolder(@NonNull View itemView, TrackClickListener listener) {
            super(itemView);
            mImageArtwork = itemView.findViewById(R.id.image_item_artwork);
            mTextTitle = itemView.findViewById(R.id.text_item_title);
            mTextArtist = itemView.findViewById(R.id.text_item_artist);
            mImageFavorite = itemView.findViewById(R.id.image_item_favorite);
            mImageMore = itemView.findViewById(R.id.image_item_more);
            mListener = listener;
            mImageFavorite.setOnClickListener(this);
            mImageMore.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void setData(Track track) {
            mTrack = track;
            RequestOptions options = new RequestOptions().centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.image_default_artwork);
            Glide.with(mImageArtwork)
                    .load(mTrack.getArtworkUrl())
                    .apply(options)
                    .into(mImageArtwork);
            mTextTitle.setText(track.getTitle());
            mTextArtist.setText(track.getArtist());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_item_favorite:
                    mListener.onFavoriteClick(mTrack);
                    break;
                case R.id.image_item_more:
                    mListener.onMoreClick(mTrack);
                    break;
                default:
                    mListener.onTrackClick(mTrack);
                    break;
            }
        }
    }

    public interface TrackClickListener {
        void onTrackClick(Track track);

        void onFavoriteClick(Track track);

        void onMoreClick(Track track);
    }
}
