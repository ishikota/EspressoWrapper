package com.ikota.espressowrapper.example.model;

import java.util.ArrayList;

public class Photo {
    public String url;
    public String title;
    public int like;
    public ArrayList<Tag> tags = new ArrayList<>();
    public ArrayList<Comment> comments = new ArrayList<>();

    public static class Tag {
        public Tag(String author_id, String content) {
            this.author_id = author_id;
            this.content   = content;
        }
        public String author_id;
        public String content;
    }

    public static class Comment {
        public Comment(String author_id, String author_name, String content, String time) {
            this.author_id   = author_id;
            this.author_name = author_name;
            this.content     = content;
            this.time        = time;
        }
        public String author_id;
        public String author_name;
        public String content;
        public String time;
    }

    public static Photo createFakeData() {
        Photo photo = new Photo();
        photo.url = "http://www.technobuffalo.com/wp-content/uploads/2015/08/Android-Marshmallow-10-1280x855.jpg";
        photo.title = "Reactive photo";
        photo.like  = 53;
        photo.tags.add(new Tag("foo", "Re"));
        photo.tags.add(new Tag("bar", "a"));
        photo.tags.add(new Tag("foo","ctive"));
        photo.comments.add(new Comment("foo", "someone", "This is Reactive !!", "1m ago"));
        photo.comments.add(new Comment("bar", "anyone" , "What a Reactive !!", "2m ago"));
        return photo;
    }
}
