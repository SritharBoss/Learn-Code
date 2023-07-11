package com.example.pixabayactivity;

class ExampleItem
{
    private String mImageUrl;
    private String mCreator;
    private int mLikes;
    private String largeImageURL;

    ExampleItem(String imageUrl, String creator, int likes,String largeImageURL) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
        this.largeImageURL=largeImageURL;
    }

    String getLargeImageURL() {
        return largeImageURL;
    }

    String getImageUrl() {
        return mImageUrl;
    }

    String getCreator() {
        return mCreator;
    }

    int getLikeCount() {
        return mLikes;
    }
}
