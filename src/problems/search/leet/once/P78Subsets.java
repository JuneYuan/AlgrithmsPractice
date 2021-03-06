package problems.search.leet.once;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

// 迭代写法
public class P78Subsets {

	/**
	 * 迭代
	 */
	public List<List<Integer>> iterativeSln(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		if (nums == null || nums.length == 0)	return result;
		
		Arrays.sort(nums);
		// 每个子集都对应[0, 2^len]中的唯一“编码”
		int cnt = (int) Math.pow(2, nums.length);
		for (int i = 0; i < cnt; i++) {
			List<Integer> temp = new ArrayList<>();
			for (int j = 0; j < nums.length; j++) {
				if ((i & (1 << j)) != 0) {
					temp.add(nums[j]);
				}
			}
			
			result.add(temp);
		}
		
		return result;
	}


	/**
	 * 递归
	 */
	public List<List<Integer>> recursiveSln(int[] nums) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (nums == null || nums.length == 0)  return result;

		Arrays.sort(nums);
		List<Integer> list = new ArrayList<>();
		// 对nums从0下标开始搜索，并将找到的子集存放到result中，当前子集为list
		dfs(nums, 0, list, result);

		return result;
	}

	/* Gitbook的递归函数
	 * 不一定要枚举完每一个数，枚举了前i位，且选了第i位时，就把它加入最后的答案。
	 * 这是正确的，因为，子集的子集一定也是最终集合的子集。
	 */
	private void dfs(int[] nums, int pos, List<Integer> list, List<List<Integer>> result) {
		// add tmp result first
		result.add(new ArrayList<Integer>(list));

		for (int i = pos; i < nums.length; i++) {
			list.add(nums[i]);
			dfs(nums, i + 1, list, result);
			list.remove(list.size() - 1);
		}
	}

	/* 自己写的递归函数
	 * 对于nums[]的每个数，枚举选和不选，一直枚举到最后，就得到一个子集。
	 */
	private void dfs_mine(int[] nums, int idx, List<Integer> curr, List<List<Integer>> result) {
		if (idx == nums.length)	{
			result.add(new ArrayList<>(curr));
			return;
		}

		// 不将nums[idx]加入子集
		dfs_mine(nums, idx + 1, curr, result);

		// 将nums[idx]加入子集
		curr.add(nums[idx]);
		dfs_mine(nums, idx + 1, curr, result);
		curr.remove(curr.size() - 1);
	}


	@Test
	public void test() {
		int[] nums = new int[]{1, 2, 3};
		for(List<Integer> list : iterativeSln(nums)) {
			System.out.println(list);
		}
	}
	
}
