package com.mountainframework.common;

import java.util.List;

import com.google.common.base.Splitter;
import com.mountainframework.common.bean.AddressSplitResult;

/**
 * String字符串处理工具类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class StringPatternUtils {

	public static AddressSplitResult splitAddress(String address, String delimiter) {
		List<String> splitResultList = Splitter.on(delimiter).omitEmptyStrings().splitToList(address);
		String left = null;
		String right = null;
		if (!CollectionUtils.isEmpty(splitResultList)) {
			if (splitResultList.size() > 1) {
				left = splitResultList.get(0);
				right = splitResultList.get(1);
			} else {
				right = splitResultList.get(0);
			}
		}
		return new AddressSplitResult(left, right);
	}

}
