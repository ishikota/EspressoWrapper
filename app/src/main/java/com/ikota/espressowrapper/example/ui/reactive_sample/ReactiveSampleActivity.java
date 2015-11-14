package com.ikota.espressowrapper.example.ui.reactive_sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ikota.espressowrapper.R;
import com.ikota.espressowrapper.example.AndroidApplication;
import com.ikota.espressowrapper.example.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

@SuppressWarnings("unused")
public class ReactiveSampleActivity extends BaseActivity implements ReactiveSampleContract.View{

    private ReactiveSampleContract.UserActionsListener mActionsListener;

    @InjectView(R.id.image) ImageView mImageView;

    @InjectView(R.id.title)       TextView mTitleView;
    @InjectView(R.id.like_num)    TextView mLikeNumView;
    @InjectView(R.id.comment_num) TextView mCommentNumView;

    @InjectView(R.id.tag_edit)     EditText mTagEdit;
    @InjectView(R.id.comment_edit) EditText mCommentEdit;

    @InjectView(R.id.like_btn)        Button mLikeBtn;
    @InjectView(R.id.add_tag_btn)     Button mAddTagBtn;
    @InjectView(R.id.add_comment_btn) Button mAddComemntBtn;

    @InjectView(R.id.tag_parent)          LinearLayout mTagParent;
    @InjectView(R.id.comment_parent)      LinearLayout mCommentParent;
    @InjectView(R.id.tag_edit_parent)     LinearLayout mTagEditParent;
    @InjectView(R.id.comment_edit_parent) LinearLayout mCommentEditParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reactive_activity);
        ButterKnife.inject(this);

        AndroidApplication app = (AndroidApplication)getApplication();
        mActionsListener = new ReactiveSamplePresenter(app.api(), this);
        mActionsListener.loadPhoto();
    }

    @Override
    public void setImage(String url) {
        Picasso.with(ReactiveSampleActivity.this)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(mImageView);
    }

    @Override
    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    @Override
    public void setLikeNum(int like_num) {
        mLikeNumView.setText(like_num+" likes");
    }

    @Override
    public void setCommentNum(int comment_num) {
        mCommentNumView.setText(comment_num + " comments posted");
    }

    @Override
    public void toggleLikeBtn(boolean be_like) {
        String msg = be_like ? "like" : "unlike";
        mLikeBtn.setText(msg);
    }

    @Override
    public void toggleAddTagBtn(boolean show_edit) {
        String msg = show_edit ? "Finish Edit" : "Add Tag";
        mAddTagBtn.setText(msg);
    }

    @Override
    public void toggleAddCommentBtn(boolean show_edit) {
        String msg = show_edit ? "Finish Edit" : "Add Comment";
        mAddComemntBtn.setText(msg);
    }

    @Override
    public void toggleEditTagVisibility(boolean show) {
        mTagEditParent.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void toggleEditCommentVisibility(boolean show) {
        mCommentEditParent.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void addTag(String content) {
        mTagParent.addView(createTagView(content));
        mTagParent.addView(createSpace());
    }

    @Override
    public void addMyTag(String content) {
        mTagParent.addView(createMyTagView(content));
        mTagParent.addView(createSpace());
    }

    @Override
    public void addComment(String user_name, String content) {
        mCommentParent.addView(createCommentView(user_name, content), 0);
    }

    @Override
    public void addMyComment(String user_name, String content) {
        mCommentParent.addView(createMyCommentView(user_name, content), 0);
    }

    @Override
    public void removeTag(int index) {
        int adjusted_index = index*2; // take care about space view
        mTagParent.removeViewAt(adjusted_index);
        mTagParent.removeViewAt(adjusted_index);
    }

    @Override
    public void removeComment(int index) {
        int adjusted_index = mCommentParent.getChildCount() - index - 1;
        mCommentParent.removeViewAt(adjusted_index);
    }

    @Override
    public void resetTagInput() {
        mTagEdit.setText("");
    }

    @Override
    public void resetCommentInput() {
        mCommentEdit.setText("");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(ReactiveSampleActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.like_btn)
    public void onClickLikeBtn(Button btn) {
        mActionsListener.clickLike(btn);
    }

    @OnClick(R.id.add_tag_btn)
    public void onClickAddTagBtn(Button btn) {
        mActionsListener.clickAddTag(btn);
    }

    @OnClick(R.id.add_comment_btn)
    public void onClickAddCommentBtn(Button btn) {
        mActionsListener.clickAddComment(btn);
    }

    @OnClick(R.id.tag_send)
    public void createNewTag(Button btn) {
        mActionsListener.clickTagSend(btn);
    }

    @OnClick(R.id.comment_send)
    public void createNewComment(Button btn) {
        mActionsListener.clickCommentSend(btn);
    }

    @OnTextChanged(R.id.tag_edit)
    public void onTagInputChange(CharSequence text) {
        mActionsListener.inputTag(text.toString());
    }

    @OnTextChanged(R.id.comment_edit)
    public void onCommentInputChange(CharSequence text) {
        mActionsListener.inputComment(text.toString());
    }

    private View createTagView(String content) {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.tag, null);
        ((TextView)view.findViewById(R.id.content)).setText(content);
        return view;
    }

    private View createMyTagView(String content) {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.mytag, null);
        ((TextView)view.findViewById(R.id.content)).setText(content);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionsListener.clickMyTag(view);
            }
        });
        return view;
    }

    private View createCommentView(String user_name, String content) {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.comment, null);
        ((TextView)view.findViewById(R.id.name)).setText(user_name);
        ((TextView)view.findViewById(R.id.content)).setText(content);
        return view;
    }

    private View createMyCommentView(String user_name, String content) {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.mycomment, null);
        ((TextView)view.findViewById(R.id.name)).setText(user_name);
        ((TextView)view.findViewById(R.id.content)).setText(content);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionsListener.clickMyComment(view);
            }
        });
        return view;
    }

    @SuppressLint("InflateParams")
    private View createSpace() {
        return getLayoutInflater().inflate(R.layout.space, null);
    }

}
