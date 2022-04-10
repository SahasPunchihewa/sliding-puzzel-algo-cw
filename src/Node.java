public class Node {

    private Integer id;
    private Integer x;
    private Integer y;
    private Character letter;

    public Node(Integer id, Integer x, Integer y, Character letter) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", letter=" + letter +
                '}';
    }
}
