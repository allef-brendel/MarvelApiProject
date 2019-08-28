package com.e.marvelapiproject.contentprovider;

import android.net.Uri;

public class Authority {

    public static final String AUTHORITY = "com.e.marvelapiproject.contentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/quadrinhos");

    public static final int QUADRINHO = 1;
    public static final int QUADRINHO_ID = 2;

}
