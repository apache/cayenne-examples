package org.apache.cayenne.demo.model.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.value.Wkt;
import org.apache.cayenne.exp.property.BaseProperty;
import org.apache.cayenne.exp.property.DateProperty;
import org.apache.cayenne.exp.property.NumericIdProperty;
import org.apache.cayenne.exp.property.PropertyFactory;

/**
 * Class _TestGeo was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _TestGeo extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final NumericIdProperty<Integer> ID_PK_PROPERTY = PropertyFactory.createNumericId("id", "TestGeo", Integer.class);
    public static final String ID_PK_COLUMN = "id";

    public static final BaseProperty<Wkt> AREA = PropertyFactory.createBase("area", Wkt.class);
    public static final DateProperty<LocalDateTime> DATE = PropertyFactory.createDate("date", LocalDateTime.class);

    protected Wkt area;
    protected LocalDateTime date;


    public void setArea(Wkt area) {
        beforePropertyWrite("area", this.area, area);
        this.area = area;
    }

    public Wkt getArea() {
        beforePropertyRead("area");
        return this.area;
    }

    public void setDate(LocalDateTime date) {
        beforePropertyWrite("date", this.date, date);
        this.date = date;
    }

    public LocalDateTime getDate() {
        beforePropertyRead("date");
        return this.date;
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "area":
                return this.area;
            case "date":
                return this.date;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "area":
                this.area = (Wkt)val;
                break;
            case "date":
                this.date = (LocalDateTime)val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.area);
        out.writeObject(this.date);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.area = (Wkt)in.readObject();
        this.date = (LocalDateTime)in.readObject();
    }

}