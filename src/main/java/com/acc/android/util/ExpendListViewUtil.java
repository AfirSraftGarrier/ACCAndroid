/**
 * 
 * ACCAFrame - ACC Android Development Platform
 * Copyright (c) 2014, AfirSraftGarrier, afirsraftgarrier@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.acc.android.util;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ExpendListViewUtil {
	public interface OnGroupActionListener {
		void OnGroupCollapse(int groupPosition);

		void OnGroupExpand(int groupPosition);

		void OnGroupUpdate();
	}

	public static void registSingleGroupAction(
			final ExpandableListView expandableListView,
			final OnGroupActionListener onGroupActionListener) {
		expandableListView
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int groupPosition) {
						onGroupActionListener.OnGroupCollapse(groupPosition);
					}
				});
		expandableListView
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						onGroupActionListener.OnGroupExpand(groupPosition);
						// int groupCount = expandableListView.getAdapter()
						// .getCount();
						// for (int i = 0; i < groupCount; i++) {
						// if (groupPosition != i) {
						// expandableListView.collapseGroup(i);
						// }
						// }
						collapseAllExcept(expandableListView, groupPosition);
					}
				});
	}

	public static void registGroupAction(
			final ExpandableListView expandableListView,
			final OnGroupActionListener onGroupActionListener) {
		expandableListView
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int groupPosition) {
						onGroupActionListener.OnGroupCollapse(groupPosition);
					}
				});
		expandableListView
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						onGroupActionListener.OnGroupExpand(groupPosition);
						// int groupCount = expandableListView.getAdapter()
						// .getCount();
					}
				});
	}

	public static void expendAll(ExpandableListView expandableListView) {
		if (expandableListView == null
				|| expandableListView.getAdapter() == null) {
			return;
		}
		int groupCount = expandableListView.getAdapter().getCount();
		// System.out.println("+++++++++++++");
		// System.out.println(groupCount);
		for (int i = 0; i < groupCount; i++) {
			try {
				expandableListView.expandGroup(i);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void collapseAllExcept(ExpandableListView expandableListView,
			int index) {
		int groupCount = expandableListView.getAdapter().getCount();
		for (int i = 0; i < groupCount; i++) {
			if (index != i) {
				expandableListView.collapseGroup(i);
			}
		}
	}

	public static void collapseAll(ExpandableListView expandableListView) {
		int groupCount = expandableListView.getAdapter().getCount();
		for (int i = 0; i < groupCount; i++) {
			expandableListView.collapseGroup(i);
		}
	}
}