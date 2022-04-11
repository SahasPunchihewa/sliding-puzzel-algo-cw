/*
Name    : Pramuditha Sahas
IIT Id  : 20201214
UOW Id  : w1810601
*/

import java.util.Objects;

public class Vertex {

    private Integer id;

    public Vertex(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || object.getClass() != this.getClass())
            return false;
        Vertex vertex = (Vertex) object;
        return (Objects.equals(vertex.id, this.id));
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
