package org.apache.cayenne.demo.model;

import org.apache.cayenne.demo.model.auto._Painting;

public class Painting extends _Painting {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Painting{" +
                "title='" + title + '\'' +
                ", objectId=" + objectId +
                ", artist=" + getArtist().getName() +
                '}';
    }
}
