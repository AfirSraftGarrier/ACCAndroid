/**
 * 
 * ACCAndroid - ACC Android Development Platform
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
package com.acc.android.util.widget.adapter.base;

import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.acc.java.util.ListUtil;

public abstract class ACCBaseExpendAdapter<T, K> extends
		BaseExpandableListAdapter {
	protected Context context;
	protected final List<T> values;
	// private final OnItemClickListener<T> onItemClickListener;
	// protected ViewHolderCallBack<T, K> ViewHolderCallBack;
	private final int groupLayoutId;
	private final int childLayoutId;

	// private boolean isFixChildView;
	// private View fixedChildView;

	// protected boolean isUseDefaultBackground;

	// protected int defaultTouchColor;

	// private final int tagKey;

	// private ViewHolder viewHolder;

	// private class ViewHolder {
	// private ImageView photoImageView;
	// private ImageView stateImageView;
	// // private TextView tv;
	// private TextView descriptionTextView;
	// private TextView stateTextView;
	// private TextView timeTextView;
	// private TextView rebackTextView;
	// private View sentBackView;
	// }

	// public void enableFixChildView() {
	// this.isFixChildView = true;
	// }

	public ACCBaseExpendAdapter(Context context, List<T> ts,
	// OnItemClickListener<T> onItemClickListener,
	// ViewHolderCallBack<T, K> viewHolderCallBack,
			Integer groupLayoutId, Integer childLayoutId
	// , Integer defaultTouchColor
	) {
		this.context = context;
		this.values = ts;
		this.groupLayoutId = groupLayoutId;
		this.childLayoutId = childLayoutId;
		// this.isFixChildView = false;
		// this.onItemClickListener = onItemClickListener;
		// this.ViewHolderCallBack = viewHolderCallBack;
		// this.itemLayoutId = itemLayoutId;
		// this.isUseDefaultBackground = true;
		// this.defaultTouchColor = defaultTouchColor == null ? R.color.gray
		// : defaultTouchColor;
		// this.tagKey = 1;
	}

	public int getIndex(T t) {
		return this.values.indexOf(t);
	}

	// public void setData(ArrayList<Map<String, Object>> attentionList) {
	// this.attentionList = attentionList;
	// countPage();
	// }

	// @Override
	// public int getCount() {
	// if (!ListUtil.isEmpty(this.values)) {
	// return this.values.size();
	// }
	// return 0;
	// // int ori = VIEW_COUNT * index;
	// // if (attentionList.size() - ori < VIEW_COUNT) {
	// // return attentionList.size() - ori;
	// // } else {
	// // return VIEW_COUNT;
	// // }
	// }

	// @Override
	// public T getItem(int position) {
	// if (ListUtil.isEmpty(this.values) || position > this.getCount() - 1) {
	// return null;
	// }
	// return this.values.get(position);
	// // return this.attentionList.get(position);
	// }

	// @Override
	// public long getItemId(int position) {
	// return position;
	// }

	// public interface OnItemClickListener<T> {
	// void onItemClick(T t);
	// }

	// public abstract// public interface ViewHolderCallBack<K, T> {
	// Object getViewHolder(View view);

	// public abstract void updateViewHolder(Object holderObject, T t);

	public abstract void updateGroupView(View convertView, boolean isExpanded,
			T t);

	public abstract void updateChildView(View convertView, K k,
			int groupPosition, int childPosition);

	public static <T extends View> T getView(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	// }

	// @Override
	// public View getView(final int position, View convertView, ViewGroup
	// parent) {
	// if (ListUtil.isEmpty(this.values) || position > this.getCount() - 1) {
	// return null;
	// }
	// // Object holderObject;
	// if (convertView == null) {
	// LayoutInflater mlnflater = LayoutInflater.from(context);
	// convertView = mlnflater.inflate(this.itemLayoutId, null);
	// if (ACCBaseExpendAdapter.this.isUseDefaultBackground) {
	// convertView.setBackgroundResource(ResourceUtil.getDrawableId(
	// context, "background_item"));
	// }
	// // // k = this.ViewHolderCallBack.getViewHolder(convertView);
	// // viewHolder = new ViewHolder();
	// // // viewHolder.tv = (TextView)
	// // // convertView.findViewById(R.id.book_name);
	// // viewHolder.photoImageView = (ImageView) convertView
	// // .findViewById(R.id.book_image);
	// // viewHolder.stateImageView = (ImageView) convertView
	// // .findViewById(R.id.state_image);
	// // viewHolder.descriptionTextView = (TextView) convertView
	// // .findViewById(R.id.book_name);
	// // viewHolder.stateTextView = (TextView) convertView
	// // .findViewById(R.id.type_text);
	// // viewHolder.timeTextView = (TextView) convertView
	// // .findViewById(R.id.time_create);
	// // viewHolder.rebackTextView = (TextView) convertView
	// // .findViewById(R.id.type_text_reback_note);
	// // viewHolder.sentBackView = convertView
	// // .findViewById(R.id.ll_type_text_reback_note);
	// // viewHolder.descriptionTextView.setSelected(true);
	// // viewHolder.rebackTextView.setSelected(true);
	// // viewHolder.rebackTextView
	// // convertView.setOnTouchListener(new OnTouchListener() {
	// // @Override
	// // public boolean onTouch(View view, MotionEvent event) {
	// // // System.out.println("xxxxx");
	// // // System.out.println(event.getAction() ==
	// // // MotionEvent.ACTION_UP);
	// // if (event.getAction() == MotionEvent.ACTION_UP
	// // || event.getAction() == MotionEvent.ACTION_CANCEL) {
	// // view.setBackgroundDrawable(null);
	// // } else {
	// // view.setBackgroundResource(ACCBaseAdapter.this.defaultTouchColor
	// // // R.color.gray
	// // );
	// // }
	// // return false;
	// // }
	// // });
	// convertView.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (ACCBaseExpendAdapter.this.onItemClickListener != null
	// && !ListUtil.isEmpty(values)) {
	// ACCBaseExpendAdapter.this.onItemClickListener
	// .onItemClick(ACCBaseExpendAdapter.this.values
	// .get((Integer) v.getTag(// R.id.tag_adapter_key
	// ResourceUtil.getId(context,
	// "tag_adapter_key"))));
	// // System.out.println(position);
	// }
	// }
	// });
	// // holderObject = this.getViewHolder(convertView);
	// // convertView.setTag(holderObject);
	// // convertView.setTag(k);
	// }
	// convertView.setTag(
	// // R.id.tag_adapter_key
	// ResourceUtil.getId(context, "tag_adapter_key")
	// // this.tagKey
	// , position);
	// // else {
	// // holderObject = convertView.getTag();
	// // }
	// // T t = this.values.get(position);
	// // this.updateViewHolder(holderObject, this.values.get(position));
	// this.updateView(convertView, this.getItem(position));
	// // viewHolder.photoImageView.setImageResource(R.drawable.email1);
	// // // viewHolder.stateImageView.setImageResource(Integer.valueOf(String
	// // // .valueOf(map.get("StateImage"))));
	// // viewHolder.descriptionTextView.setText(task.getDescription()
	// // // String.valueOf(map
	// // // .get("Item_taskType"))
	// // );
	// // viewHolder.stateTextView.setText("浠诲姟鐘舵�侊細"
	// // + TaskUtil.getTaskStatusString(task.getTaskStatus())
	// // // String.valueOf(map
	// // // .get("Item_upstatus"))
	// // );
	// // viewHolder.timeTextView.setText(TimeUtil.dateToAppString(task
	// // .getTaskTime())
	// // // String.valueOf(map.get("Item_taskTime"))
	// // );
	// // if (task.getIsPass() == DatabaseRelateConstant.TASK_ISPASS_NO
	// // // && !StringUtil.isEmpty(task.getDealContent())
	// // ) {
	// // viewHolder.sentBackView.setVisibility(View.VISIBLE);
	// // viewHolder.rebackTextView.setText(StringUtil.getNotNullString(
	// // task.getDealContent(), ""));
	// // }
	// // convertView.setBackgroundResource(R.drawable.bg_task_list_item);
	// // convertView.setTag(key, tag);
	// // Map<String, Object> map = attentionList.get(position + index
	// // * VIEW_COUNT);
	// // viewHolder.tv.setText(String.valueOf(map.get("Item_taskNO")));
	// // imageLoader.DisplayImage(record.getItemImage(), activity,
	// // viewHolder.iv);
	// // imageManage2.displayImage(viewHolder.iv, record.getItemImage(),
	// // R.drawable.image_indicator, 100, 100);
	// return convertView;
	// }

	public void clear() {
		if (!ListUtil.isEmpty(this.values)) {
			this.values.clear();
		}
	}

	public void add(List<T> ts) {
		if (!ListUtil.isEmpty(ts)) {
			this.values.addAll(ts);
		}
	}

	@Override
	public K getChild(int groupPosition, int childPosition) {
		// return groups.get(groupPosition).getFeatures().get(childPosition);
		return this.getChild(this.values.get(groupPosition), childPosition);
	}

	public abstract K getChild(T t, int childPosition);

	// @Override
	// public long getChildId(int groupPosition, int childPosition) {
	// return childPosition;
	// }

	// private void makesureInitFixedChildView() {
	// if (this.fixedChildView == null) {
	// this.fixedChildView = LayoutInflater.from(context).inflate(
	// this.childLayoutId, null);
	// }
	// }

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// if (ListUtil.isEmpty(this.values) || position > this.getCount() - 1)
		// {
		// return null;
		// }
		// Object holderObject;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					this.childLayoutId, null);
		}
		// View targetView = convertView;
		// if (this.isFixChildView) {
		// this.makesureInitFixedChildView();
		// targetView = this.fixedChildView;
		// } else if (targetView == null) {
		// targetView = LayoutInflater.from(context).inflate(
		// this.childLayoutId, null);
		// }
		// if (this.isFixChildView) {
		//
		// }
		// if (convertView == null) {
		// // System.out.println("NEWNWENWENNEWEWENEN");
		// // LayoutInflater mlnflater = LayoutInflater.from(context);
		// convertView = LayoutInflater.from(context).inflate(
		// this.childLayoutId, null);
		// }
		// ExpandableListHolder holder = null;
		// try {
		// if (convertView == null) {
		// convertView = mChildInflater.inflate(R.layout.expendable_item4,
		// null);
		// holder = new ExpandableListHolder();
		// holder.tvName = (TextView) convertView
		// .findViewById(R.id.TextView01);
		// holder.imageView = (ImageView) convertView
		// .findViewById(R.id.imageview01);
		// convertView.setTag(holder);
		// } else {
		// holder = (ExpandableListHolder) convertView.getTag();
		// }
		// final Feature info = this.groups.get(groupPosition).getFeatures()
		// .get(childPosition);
		// // final int gpos = groupPosition;
		// setCheck_imageView(holder.imageView);
		// if (info != null) {
		// holder.tvName.setText((String) info.getValues().get(
		// groups.get(groupPosition).getAnnoFieldName()));
		// if (info.ischeck) {
		// holder.imageView.setVisibility(View.VISIBLE);
		// } else {
		// holder.imageView.setVisibility(View.GONE);
		// }
		// // holder.radioButton.setChecked(info.checked);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println("VVVVVVVVVV1111VVVVVVVVVVVVV");
		// System.out.println(groupPosition);
		// System.out.println(childPosition);
		this.updateChildView(convertView,
				this.getChild(groupPosition, childPosition), groupPosition,
				childPosition);
		// if (this.isFixChildView) {
		// return this.fixedChildView;
		// }
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// return groups.get(groupPosition).getFeatures().size();
		return this.getChildrenCount(this.values.get(groupPosition));
	}

	public abstract int getChildrenCount(T t);

	@Override
	public T getGroup(int groupPosition) {
		// return groups.get(groupPosition);
		return this.values.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// return groups.size();
		return this.values.size();
	}

	// @Override
	// public long getGroupId(int groupPosition) {
	// return 0;
	// }

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			// LayoutInflater mlnflater = LayoutInflater.from(context);
			convertView = LayoutInflater.from(context).inflate(
					this.groupLayoutId, null);
		}
		// ExpandableListHolder holder = null;
		// try {
		// if (convertView == null) {
		// convertView = mChildInflater.inflate(R.layout.expendable_item3,
		// null);
		// holder = new ExpandableListHolder();
		// holder.tvName = (TextView) convertView
		// .findViewById(R.id.TextView01);
		// convertView.setTag(holder);
		// } else {
		// holder = (ExpandableListHolder) convertView.getTag();
		// }
		// FeatureSet info = this.groups.get(groupPosition);
		// // final int pos = groupPosition;
		// if (info != null) {
		// holder.tvName.setText(info.getLayerAliases() + "("
		// + this.groups.get(groupPosition).getFeatures().size()
		// + ")");
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		this.updateGroupView(convertView, isExpanded,
				this.getGroup(groupPosition));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public long getGroupId(int i) {
		return 0;
	}

	@Override
	public long getChildId(int i, int j) {
		return 0;
	}

	@Override
	public boolean isChildSelectable(int i, int j) {
		return true;
	}

	// @Override
	// public boolean hasStableIds() {
	// return false;
	// }
}