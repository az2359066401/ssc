package com.vivo.bigdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Solution {


    public static int maxProfit(int[] prices) {
        if (prices.length <= 1)
            return 0;
        int buy = -prices[0], sell = 0;
        for (int i = 1; i < prices.length; i++) {
            buy = Math.max(buy, -prices[i]);
            sell = Math.max(sell, prices[i] + buy);
        }
        return sell;
    }


    public static void main1(String[] args) throws Exception {
        int[] prices = {2, 43, 5, 6, 21, 5, 6};
        int i = maxProfit(prices);
        System.out.println(i);
        int[] pricesx = {2, 43, 5, 54, 2, 6, 73, 6, 76, 5446, 6, 6, 21, 5, 6};
        int ix = maxProfit(pricesx);
        System.out.println(ix);
        int ixx = maxProfit1(pricesx);
        System.out.println(ixx);
        System.out.println(JSON.toJSONString(bubblesort(pricesx)));
        System.out.println(JSON.toJSONString(selectSort(pricesx)));
        System.out.println(JSON.toJSONString(insertSort(pricesx)));
        System.out.println(JSON.toJSONString(shellSort(pricesx)));
        System.out.println(JSON.toJSONString(mergeSort(pricesx)));
        System.out.println(JSON.toJSONString(quickSort(pricesx)));
        System.out.println(JSON.toJSONString(heapSort(pricesx)));
        System.out.println(JSON.toJSONString(countSort(pricesx)));
        System.out.println(JSON.toJSONString(bucketSort(pricesx)));
        System.out.println(JSON.toJSONString(RadixSort(pricesx)));
    }


    public static int maxProfit1(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int buy = -prices[0], sell = 0;
        for (int i = 1; i < prices.length; i++) {
            sell = Math.max(sell, prices[i] + buy);
            buy = Math.max(buy, sell - prices[i]);
        }
        return sell;
    }


    public static int maxProfit2(int[] prices) {
        int fstBuy = Integer.MIN_VALUE, fstSell = 0;
        int secBuy = Integer.MIN_VALUE, secSell = 0;
        for (int i = 0; i < prices.length; i++) {
            fstBuy = Math.max(fstBuy, -prices[i]);
            fstSell = Math.max(secSell, fstBuy + prices[i]);
            secBuy = Math.max(secBuy, fstSell - prices[i]);
            secSell = Math.max(secSell, secBuy + prices[i]);
        }
        return secSell;
    }


    //    . 冒泡排序
    public static int[] bubblesort(int[] sourceArray) throws Exception {
        //对arr 进行拷贝  不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        for (int i = 1; i < arr.length; i++) {
            boolean flag = true;

            for (int j = 0; j < arr.length - i; j++) {

                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = false;
                }

            }
            if (flag) {
                break;
            }
        }
        return arr;
    }


    //. 选择排序
    public static int[] selectSort(int[] sourceArray) throws Exception {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        //总共要经过N-1轮比较
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;

            //每轮需要比较的次数 N-I
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    //记录目前能找到的最小值元素的下标
                    min = j;
                }
            }
            //将找到的最小值和i位置所在的值进行交换
            if (i != min) {
                int tmp = arr[i];
                arr[i] = arr[min];
                arr[min] = tmp;
            }
        }
        return arr;
    }

//插入排序

    public static int[] insertSort(int[] sourceArray) throws Exception {
        //对arr 进行拷贝  不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        //从下标为1 的元素开始选择合适的位置插入  因为下标为0的只有一个元素 默认时有序的
        for (int i = 1; i < arr.length; i++) {

            //记录要插入的数据
            int tmp = arr[i];

            //从已经排序的序列最右边开始比较 找到比其小的数
            int j = i;
            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            //存在比其小的数  插入
            if (j != i) {
                arr[j] = tmp;
            }
        }
        return arr;
    }

//    . 希尔排序

    public static int[] shellSort(int[] sourceArray) throws Exception {
        //对 arr进行拷贝 不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int gap = 1;
        while (gap < arr.length) {
            gap = gap * 3 + 1;
        }

        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (int) Math.floor(gap / 3);
        }
        return arr;
    }

    //. 归并排序
    public static int[] mergeSort(int[] sourceArray) throws Exception {

        //对arr 进行拷贝 不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        if (arr.length < 2) {
            return arr;
        }

        int middle = (int) Math.floor(arr.length / 2);

        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);
        return merge(mergeSort(left), mergeSort(right));


    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (left[0] <= right[0]) {
                result[i++] = left[0];
                left = Arrays.copyOfRange(left, 1, left.length);
            } else {
                result[i++] = right[0];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }
        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }
        return result;
    }

//. 快速排序

    public static int[] quickSort(int[] sourceArray) throws Exception {

        //对 arr 进行拷贝 不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        return quickSortExt(arr, 0, arr.length - 1);

    }

    private static int[] quickSortExt(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSortExt(arr, left, partitionIndex - 1);
            quickSortExt(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        //设定基准值  pivot
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    //7. 堆排序
    public static int[] heapSort(int[] sourceArray) throws Exception {
        //对 arr 进行拷贝 不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int len = arr.length;

        buildMaxHeap(arr, len);

        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            len--;
            heapify(arr, 0, len);
        }
        return arr;
    }

    private static void buildMaxHeap(int[] arr, int len) {
        for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    private static void heapify(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }


        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, len);
        }
    }

    //. 计数排序
    public static int[] countSort(int[] sourceArray) throws Exception {
        //对 arr进行拷贝 不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int maxValue = getMaxValue(arr);
        return countingSort(arr, maxValue);
    }

    private static int[] countingSort(int[] arr, int maxValue) {
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];

        for (int value : arr) {
            bucket[value]++;
        }

        int sortedIndex = 0;
        for (int j = 0; j < bucketLen; j++) {
            while (bucket[j] > 0) {
                arr[sortedIndex++] = j;
                bucket[j]--;
            }
        }
        return arr;

    }

    private static int getMaxValue(int[] arr) {
        int maxValue = arr[0];
        for (int value : arr) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    //. 桶排序
    public static int[] bucketSort(int[] sourceArray) throws Exception {
        //对arr 数据进行拷贝 不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        return bucketSortExt(arr, 5);
    }

    private static int[] bucketSortExt(int[] arr, int bucketSize) throws Exception {
        if (arr.length == 0) {
            return arr;
        }

        int minValue = arr[0];
        int maxValue = arr[0];
        for (int value : arr) {
            if (value < minValue) {
                minValue = value;
            } else if (value > maxValue) {
                maxValue = value;
            }
        }

        int bucketCount = (int) Math.floor((maxValue - minValue) / bucketSize) + 1;
        int[][] buckets = new int[bucketCount][0];


        //利用映射函数将数据分配到各个桶中
        for (int i = 0; i < arr.length; i++) {
            int index = (int) Math.floor((arr[i] - minValue) / bucketSize);
            buckets[index] = arrAppend(buckets[index], arr[i]);
        }

        int arrIndex = 0;
        for (int[] bucket : buckets) {
            if (bucket.length <= 0) {
                continue;
            }
            //对每个桶进行排序  这里使用了插入排序
            bucket = insertSort(bucket);
            for (int value : bucket) {
                arr[arrIndex++] = value;
            }
        }
        return arr;
    }


    /**
     * 自动扩容 并保存数据
     *
     * @param arr
     * @param value
     * @return
     */
    private static int[] arrAppend(int[] arr, int value) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = value;
        return arr;
    }


//    . 基数排序


    public static int[] RadixSort(int[] sourceArray) throws Exception {
        //对arr 进行拷贝 不改变 参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int maxDigit = getMaxDigit(arr);
        return radixSortExt(arr, maxDigit);
    }

    private static int[] radixSortExt(int[] arr, int maxDigit) {
        int mod = 10;
        int dev = 1;
        for (int i = 0; i < maxDigit; i++, dev *= 10, mod *= 10) {
            //考虑负数的情况 这里扩展一倍队列数 其中[0-9]对应负数 [10-19]对应正数 (bucket+10)
            int[][] counter = new int[mod * 2][0];

            for (int j = 0; j < arr.length; j++) {
                int bucket = ((arr[j] % mod) / dev) + mod;
                counter[bucket] = arrAppend(counter[bucket], arr[j]);
            }

            int pos = 0;
            for (int[] bucket : counter) {
                for (int value : bucket) {
                    arr[pos++] = value;
                }
            }
        }
        return arr;
    }


    /**
     * 获取最高位数
     *
     * @param arr
     * @return
     */
    private static int getMaxDigit(int[] arr) {
        int maxValue = getMaxValue(arr);
        return getNumLength(maxValue);
    }

    private static int getNumLength(long num) {
        if (num == 0) {
            return 1;
        }
        int length = 0;
        for (long temp = num; temp != 0; temp /= 10) {
            length++;
        }
        return length;
    }






//    三数之和


    public static void main(String[] args) {


        JSONArray objects = new JSONArray();

        for (int i =12;i>=1;i--){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("createDate","2018-"+i+"-12 12:12:12");
            jsonObject.put("field"+i,"value"+i);
            objects.add(jsonObject);
        }
        System.out.println(JSON.toJSONString(objects));
        System.out.println(JSON.toJSONString(jsonArraySort(objects)));
    }

    /**
     * 按照JSONArray中的对象的某个字段进行排序(采用fastJson)
     *
     * @param jsonArr
     * @return
     */
    public static JSONArray jsonArraySort(JSONArray jsonArr) {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.add(jsonArr.getJSONObject(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            // You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "createDate";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();
                try {
                    // 这里是a、b需要处理的业务，需要根据你的规则进行修改。
                    String aStr = a.getString(KEY_NAME);
                    valA = aStr.replaceAll("-", "");
                    String bStr = b.getString(KEY_NAME);
                    valB = bStr.replaceAll("-", "");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
                    Date formatA = sdf.parse(valA);
                    Date formatB = sdf.parse(valB);
                    boolean before = formatA.before(formatB);
                    if (before) {
                        return -1;
                    } else {
                        return 1;
                    }
                } catch (JSONException e) {
                    // do something
                } catch (ParseException e) {

                }
                return -1;
                // if you want to change the sort order, simply use the following:
                // return -valA.compareTo(valB);
            }
        });
        for (int i = 0; i < jsonArr.size(); i++) {
            sortedJsonArray.add(jsonValues.get(i));
        }
        return sortedJsonArray;
    }


}
