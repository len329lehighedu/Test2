public class Movie implements Comparable<Movie>{
    private int id;
    private String title;
    private double rating;

    public Movie(int id, String title, double r) {
        this.id = id;
        this.title = title;
        rating = r;
    }
    public int getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public double getRating() {
        return rating;
    }
    public void setID(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String toString() {
        return "(" + id + ", " + title + ", " + String.format("%.1f",rating) + ')';
    }
    public boolean equals(Object o) {
        if (o instanceof Movie){
            Movie movie = (Movie) o;
            return id == movie.id;
        }
        return false;
    }
    public int compareTo(Movie m){
        return this.id - m.id;
    }
}