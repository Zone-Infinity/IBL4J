import me.infinity.ibl.IBL;

public class Posting {
    public static void main(String[] args) {
        IBL ibl = new IBL.Builder("BOT_ID", "IBL_TOKEN");

        ibl.postStats(100);
    }
}
