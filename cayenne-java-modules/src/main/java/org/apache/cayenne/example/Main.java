package org.apache.cayenne.example;

import java.util.List;

import org.apache.cayenne.example.persistent.Artist;
import org.apache.cayenne.example.persistent.Painting;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectSelect;

public class Main {
    public static void main(String[] args) {
        ServerRuntime cayenneRuntime = ServerRuntime.builder()
                .addConfig("cayenne-project.xml")
                .build();
        ObjectContext context = cayenneRuntime.newContext();
        Artist artist = context.newObject(Artist.class);
        artist.setArtistName("Pablo Picasso");

        Painting painting = context.newObject(Painting.class);
        painting.setPaintingTitle("Girl Reading at a Table");

        artist.addToPaintings(painting);

        context.commitChanges();

        List<Artist> artists = ObjectSelect.query(Artist.class)
                .prefetch(Artist.PAINTINGS.disjoint())
                .select(context);

        System.out.println(artists.get(0).getArtistName());
        System.out.println(artists.get(0).getPaintings().get(0).getPaintingTitle());
    }
}
