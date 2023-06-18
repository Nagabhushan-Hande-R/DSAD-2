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

	// Sorting campaigns based on end dates using merge sort
	public static void mergeSort(Campaign[] campaigns, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergeSort(campaigns, low, mid);
			mergeSort(campaigns, mid + 1, high);
			merge(campaigns, low, mid, high);
		}
	}

	public static void merge(Campaign[] campaigns, int low, int mid, int high) {
		int n1 = mid - low + 1;
		int n2 = high - mid;

		Campaign[] left = new Campaign[n1];
		Campaign[] right = new Campaign[n2];

		for (int i = 0; i < n1; ++i) {
			left[i] = campaigns[low + i];
		}

		for (int j = 0; j < n2; ++j) {
			right[j] = campaigns[mid + 1 + j];
		}

		int i = 0, j = 0;
		int k = low;

		while (i < n1 && j < n2) {
			if (left[i].endDate <= right[j].endDate) {
				campaigns[k] = left[i];
				i++;
			} else {
				campaigns[k] = right[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			campaigns[k] = left[i];
			i++;
			k++;
		}

		while (j < n2) {
			campaigns[k] = right[j];
			j++;
			k++;
		}
	}

	public static int scheduleCampaigns(Campaign[] campaigns) {
		// Sort campaigns based on end dates using merge sort
		mergeSort(campaigns, 0, campaigns.length - 1);

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

	// Find the index of the last non-overlapping campaign using interval scheduling
	public static int findLastNonOverlappingCampaign(Campaign[] campaigns, int currentIndex) {
		int low = 0;
		int high = currentIndex - 1;
		int lastNonOverlappingIndex = -1;

		while (low <= high) {
			int mid = (low + high) / 2;
			if (campaigns[mid].endDate <= campaigns[currentIndex].startDate) {
				lastNonOverlappingIndex = mid;
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return lastNonOverlappingIndex;
	}

	public static void main(String[] args) {
		Campaign[] campaigns = {
				new Campaign("Campaign 1", 1, 4, 100),
				new Campaign("Campaign 2", 2, 6, 200),
				new Campaign("Campaign 3", 5, 7, 150),
				new Campaign("Campaign 4", 4, 8, 300),
				new Campaign("Campaign 5", 6, 9, 500)
		};

		int maxProfit = scheduleCampaigns(campaigns);
		System.out.println("Maximum Profit: " + maxProfit);
	}
}
