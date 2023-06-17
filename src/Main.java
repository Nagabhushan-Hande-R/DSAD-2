import java.util.Arrays;
import java.util.Comparator;

public class Main {

	static class Campaign {
		String name;
		int startDate;
		int endDate;
		int profit;

		public Campaign(String name, int startDate, int endDate, int profit) {
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.profit = profit;
		}
	}

	public static int scheduleCampaigns(Campaign[] campaigns) {
		// Sort campaigns based on end dates in ascending order
		Arrays.sort(campaigns, Comparator.comparingInt(c -> c.endDate));

		int n = campaigns.length;
		int[] dp = new int[n];
		dp[0] = campaigns[0].profit; // Set initial profit

		for (int i = 1; i < n; i++) {
			int currentProfit = campaigns[i].profit;
			int index = findLastNonOverlappingCampaign(campaigns, i);
			if (index != -1) {
				currentProfit += dp[index];
			}
			dp[i] = Math.max(currentProfit, dp[i - 1]);
		}

		return dp[n - 1];
	}

	public static int findLastNonOverlappingCampaign(Campaign[] campaigns, int currentIndex) {
		for (int i = currentIndex - 1; i >= 0; i--) {
			if (campaigns[i].endDate <= campaigns[currentIndex].startDate) {
				return i;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
		Campaign[] campaigns = {
				new Campaign("Campaign 1", 1, 4, 100),
				new Campaign("Campaign 2", 2, 6, 200),
				new Campaign("Campaign 3", 5, 7, 150),
				new Campaign("Campaign 4", 4, 8, 300),
				new Campaign("Campaign 5", 6, 9, 250)
		};

		int maxProfit = scheduleCampaigns(campaigns);
		System.out.println("Maximum Profit: " + maxProfit);
	}
}
