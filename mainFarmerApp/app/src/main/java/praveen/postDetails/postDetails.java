package praveen.postDetails;

public class postDetails {
    String quantityDetails;
    String priceDetails;
    String imgurl;
    String title;
    String id;

    public postDetails(){

    }

    public postDetails(String quantityDetails, String priceDetails, String imgurl, String title, String id) {
        this.quantityDetails = quantityDetails;
        this.priceDetails = priceDetails;
        this.imgurl = imgurl;
        this.title = title;
        this.id = id;
    }

    public String getQuantityDetails() {
        return quantityDetails;
    }

    public void setQuantityDetails(String quantityDetails) {
        this.quantityDetails = quantityDetails;
    }

    public String getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(String priceDetails) {
        this.priceDetails = priceDetails;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
