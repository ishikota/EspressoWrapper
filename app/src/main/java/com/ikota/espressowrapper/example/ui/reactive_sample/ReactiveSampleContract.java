package com.ikota.espressowrapper.example.ui.reactive_sample;

import android.widget.Button;


public class ReactiveSampleContract {

    interface View {

        void setImage(String url);

        void setTitle(String title);

        void setLikeNum(int like_num);

        void setCommentNum(int comment_num);

        void toggleLikeBtn(boolean be_like);

        void toggleAddTagBtn(boolean show_edit);

        void toggleAddCommentBtn(boolean show_edit);

        void toggleEditTagVisibility(boolean show);

        void toggleEditCommentVisibility(boolean show);

        void addTag(String content);

        void addMyTag(String content);

        void addComment(String user_name, String content);

        void addMyComment(String user_name, String content);

        void removeTag(int index);

        void removeComment(int index);

        void resetTagInput();

        void resetCommentInput();

        void showToast(String message);

    }

    interface UserActionsListener {

        void loadPhoto();

        void clickLike(Button btn);

        void clickAddTag(Button btn);

        void clickAddComment(Button btn);

        void clickTagSend(Button btn);

        void clickCommentSend(Button btn);

        void clickMyTag(android.view.View view);

        void clickMyComment(android.view.View view);

        void inputTag(String state);

        void inputComment(String state);

    }

}
