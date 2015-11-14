package com.ikota.espressowrapper.example.ui.reactive_sample;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ikota.espressowrapper.R;
import com.ikota.espressowrapper.example.data.Api;
import com.ikota.espressowrapper.example.data.ApiCallback;
import com.ikota.espressowrapper.example.model.Photo;

import java.util.ArrayList;


public class ReactiveSamplePresenter implements ReactiveSampleContract.UserActionsListener{

    private final Api API;
    private final ReactiveSampleContract.View mReactiveView;

    private Photo mPhotoData;

    // My profile
    private String my_id   = "my_id";
    private String my_name = "Ishikota";

    // state variables
    private boolean showing_like       = true;
    private boolean is_editing_tag     = false;
    private boolean is_editing_comment = false;

    private String tag_editing     = "";
    private String comment_editing = "";


    public ReactiveSamplePresenter(Api api, ReactiveSampleContract.View reactive_view) {
        API = api;
        this.mReactiveView = reactive_view;
    }

    @Override
    public void loadPhoto() {
        API.callAPi(new ApiCallback() {
            @Override
            public void onResult(Photo photo) {
                mPhotoData = photo;
                mReactiveView.setImage(photo.url);
                mReactiveView.setTitle(photo.title);
                mReactiveView.setCommentNum(photo.comments.size());
                addTags(mReactiveView, photo.tags);
                addComments(mReactiveView, photo.comments);
            }
        });
    }

    @Override
    public void clickLike(Button btn) {
        if(mPhotoData == null) return;
        mPhotoData.like = showing_like ? mPhotoData.like+1 : mPhotoData.like-1;
        showing_like = !showing_like;
        mReactiveView.setLikeNum(mPhotoData.like);
        mReactiveView.toggleLikeBtn(showing_like);
    }

    @Override
    public void clickAddTag(Button btn) {
        if(mPhotoData == null) return;
        is_editing_tag = !is_editing_tag;
        mReactiveView.toggleAddTagBtn(is_editing_tag);
        mReactiveView.toggleEditTagVisibility(is_editing_tag);
    }

    @Override
    public void clickAddComment(Button btn) {
        if (mPhotoData == null) return;
        is_editing_comment = !is_editing_comment;
        mReactiveView.toggleAddCommentBtn(is_editing_comment);
        mReactiveView.toggleEditCommentVisibility(is_editing_comment);
    }

    @Override
    public void clickTagSend(Button btn) {
        if(isValidTag(tag_editing)) {
            mPhotoData.tags.add(new Photo.Tag(my_id, tag_editing));
            mReactiveView.addMyTag(tag_editing);
            mReactiveView.resetTagInput();
        } else {
            mReactiveView.showToast("Your tag is invalid !");
        }
    }

    @Override
    public void clickCommentSend(Button btn) {
        if(isValidComment(comment_editing)) {
            mPhotoData.comments.add(new Photo.Comment(my_id, my_name, comment_editing, "now"));
            mReactiveView.addMyComment(my_name, comment_editing);
            mReactiveView.setCommentNum(mPhotoData.comments.size());
            mReactiveView.resetCommentInput();
        } else {
            mReactiveView.showToast("Your comment is invalid !");
        }
    }

    @Override
    public void clickMyTag(View view) {
        String content = ((TextView)view.findViewById(R.id.content)).getText().toString();
        int index = 0;
        for (Photo.Tag tag : mPhotoData.tags) {
            if(tag.content.equals(content)) break;
            index++;
        }
        mPhotoData.tags.remove(index);
        mReactiveView.removeTag(index);
    }

    @Override
    public void clickMyComment(View view) {
        String content = ((TextView)view.findViewById(R.id.content)).getText().toString();
        int index = 0;
        for(Photo.Comment comment : mPhotoData.comments) {
            if(comment.content.equals(content)) break;
            index++;
        }
        mPhotoData.comments.remove(index);
        mReactiveView.removeComment(index);
        mReactiveView.setCommentNum(mPhotoData.comments.size());
    }

    @Override
    public void inputTag(String state) {
        tag_editing = state;
    }

    @Override
    public void inputComment(String state) {
        comment_editing = state;
    }

    private void addTags(ReactiveSampleContract.View view, ArrayList<Photo.Tag> tags) {
        for(Photo.Tag tag : tags) {
            view.addTag(tag.content);
        }
    }

    private void addComments(ReactiveSampleContract.View view, ArrayList<Photo.Comment> comments) {
        for(Photo.Comment comment : comments) {
            view.addComment(comment.author_name, comment.content);
        }
    }

    private boolean isValidTag(String tag) {
        return tag!=null && !tag.isEmpty() && tag.length()<=10;
    }

    private boolean isValidComment(String comment) {
        return comment!=null && !comment.isEmpty() && comment.length() <= 72;
    }
}
