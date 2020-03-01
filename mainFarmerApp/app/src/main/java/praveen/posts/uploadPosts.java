package praveen.posts;



public class uploadPosts {
    String id;
    String title;
    String quantity;
    String price;
    String farmerId;
    String imgurl;
    //String location;

    public uploadPosts() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public uploadPosts(String id, String title, String quantity, String price, String farmerId, String imgurl) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.farmerId = farmerId;
        this.imgurl = imgurl;
    }
}