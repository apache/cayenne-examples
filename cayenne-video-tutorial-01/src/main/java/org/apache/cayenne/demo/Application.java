package org.apache.cayenne.demo;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;
import org.apache.cayenne.demo.model.Artist;
import org.apache.cayenne.demo.model.Painting;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Application {

    private final ServerRuntime cayenneRuntime;

    public static void main(String[] args) {
        Application app = new Application();
//        app.insertArtist();
//        app.updateArtist();
//        app.insertPaintings();
        app.selectPaintings();
    }

    private Application() {
        cayenneRuntime = ServerRuntime.builder()
                .addConfig("cayenne-project.xml")
                .dataSource(DataSourceBuilder.url("jdbc:mysql://localhost/cayenne")
                        .driver(com.mysql.cj.jdbc.Driver.class.getName())
                        .userName("root")
                        .password("cayenne")
                        .pool(1, 3).build())
                .build();
    }

    private void selectPaintings() {
        ObjectContext context = cayenneRuntime.newContext();

        List<Painting> paintingList = ObjectSelect.query(Painting.class)
                .where(Painting.ESTIMATED_PRICE.gt(BigDecimal.valueOf(100_000_000)))
                .and(Painting.ARTIST.dot(Artist.DATE_OF_BIRTH).gt(LocalDate.of(1800, 1, 1)))
                .prefetch(Painting.ARTIST.joint())
                .orderBy(Painting.TITLE.asc())
                .select(context);

        paintingList.forEach(System.out::println);
    }

    private void insertPaintings() {
        ObjectContext context = cayenneRuntime.newContext();

        Artist picasso = ObjectSelect.query(Artist.class, Artist.NAME.eq("Pablo Picasso")).selectOne(context);

        Painting boy = context.newObject(Painting.class);
        boy.setTitle("Boy with a Pipe");
        boy.setEstimatedPrice(BigDecimal.valueOf(104_168_000));
        boy.setArtist(picasso);

        Painting drinker = context.newObject(Painting.class);
        drinker.setArtist(picasso);
        drinker.setTitle("Absinthe Drinker");

        context.commitChanges();
    }

    private void updateArtist() {
        ObjectContext context = cayenneRuntime.newContext();

        Artist picasso = SelectById.query(Artist.class, 1).selectOne(context);
        picasso.setDateOfBirth(LocalDate.of(1881, 10, 25));

        context.commitChanges();
    }

    private void insertArtist() {
        ObjectContext context = cayenneRuntime.newContext();

        Artist picasso = context.newObject(Artist.class);
        picasso.setName("Pablo Picasso");

        context.commitChanges();
    }

}
