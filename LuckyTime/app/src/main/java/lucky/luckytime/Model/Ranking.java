package lucky.luckytime.Model;

public class Ranking {

    private String userId;
    private long count;

    public Ranking(String userId, long count) {
        this.userId = userId;
        this.count = count;
    }

    public Ranking (){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "userId='" + userId + '\'' +
                ", count=" + count +
                '}';
    }
}
