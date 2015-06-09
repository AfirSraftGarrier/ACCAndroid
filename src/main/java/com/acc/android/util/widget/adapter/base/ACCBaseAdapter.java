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
package com.acc.android.util.widget.adapter.base;

import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.acc.android.util.ResourceUtil;
import com.acc.java.util.ListUtil;

public abstract class ACCBaseAdapter<T
// , K
> extends BaseAdapter {
	protected Context context;

	private final List<T> values;
	private final OnItemClickListener<T> onItemClickListener;
	// protected ViewHolderCallBack<T, K> ViewHolderCallBack;
	private final int itemLayoutId;
	protected boolean isUseDefaultBackground;
	private Integer tagId;
	private int actionPosition;

	public int getActionPosition() {
		return this.actionPosition;
	}

	public void setActionPosition(int position) {
		this.actionPosition = position;
	}

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

	public ACCBaseAdapter(Context context, List<T> ts,
			OnItemClickListener<T> onItemClickListener,
			// ViewHolderCallBack<T, K> viewHolderCallBack,
			Integer itemLayoutId
	// , Integer defaultTouchColor
	) {
		this.context = context;
		this.values = ts;
		this.onItemClickListener = onItemClickListener;
		// this.ViewHolderCallBack = viewHolderCallBack;
		this.itemLayoutId = itemLayoutId;
		this.isUseDefaultBackground = true;
		this.tagId = ResourceUtil.getId(context, "tag_adapter_key");
		if (this.tagId == null || this.tagId == 0) {
			this.tagId = itemLayoutId;
		}
		// System.out.println("+++++++++++++");
		// System.out.println(tagId);
		// this.defaultTouchColor = defaultTouchColor == null ? R.color.gray
		// : defaultTouchColor;
		// this.tagKey = 1;
	}

	// public void setData(ArrayList<Map<String, Object>> attentionList) {
	// this.attentionList = attentionList;
	// countPage();
	// }

	@Override
	public int getCount() {
		if (!ListUtil.isEmpty(this.values)) {
			return this.values.size();
		}
		return 0;
		// int ori = VIEW_COUNT * index;
		// if (attentionList.size() - ori < VIEW_COUNT) {
		// return attentionList.size() - ori;
		// } else {
		// return VIEW_COUNT;
		// }
	}

	@Override
	public T getItem(int position) {
		if (ListUtil.isEmpty(this.values) || position > this.getCount() - 1) {
			return null;
		}
		return this.values.get(position);
		// return this.attentionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public interface OnItemClickListener<T> {
		void onItemClick(T t);
	}

	// public abstract// public interface ViewHolderCallBack<K, T> {
	// Object getViewHolder(View view);

	// public abstract void updateViewHolder(Object holderObject, T t);

	public abstract void updateView(View convertView, T t);

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

	protected int getPosition(T t) {
		return this.values.indexOf(t);
	}

	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (ListUtil.isEmpty(this.values) || position > this.getCount() - 1) {
			return null;
		}
		// Object holderObject;
		if (convertView == null) {
			LayoutInflater mlnflater = LayoutInflater.from(context);
			convertView = mlnflater.inflate(this.itemLayoutId, null);
			if (ACCBaseAdapter.this.isUseDefaultBackground) {
				convertView.setBackgroundResource(ResourceUtil.getDrawableId(
						context, "background_item"));
			}
			// // k = this.ViewHolderCallBack.getViewHolder(convertView);
			// viewHolder = new ViewHolder();
			// // viewHolder.tv = (TextView)
			// // convertView.findViewById(R.id.book_name);
			// viewHolder.photoImageView = (ImageView) convertView
			// .findViewById(R.id.book_image);
			// viewHolder.stateImageView = (ImageView) convertView
			// .findViewById(R.id.state_image);
			// viewHolder.descriptionTextView = (TextView) convertView
			// .findViewById(R.id.book_name);
			// viewHolder.stateTextView = (TextView) convertView
			// .findViewById(R.id.type_text);
			// viewHolder.timeTextView = (TextView) convertView
			// .findViewById(R.id.time_create);
			// viewHolder.rebackTextView = (TextView) convertView
			// .findViewById(R.id.type_text_reback_note);
			// viewHolder.sentBackView = convertView
			// .findViewById(R.id.ll_type_text_reback_note);
			// viewHolder.descriptionTextView.setSelected(true);
			// viewHolder.rebackTextView.setSelected(true);
			// viewHolder.rebackTextView
			// convertView.setOnTouchListener(new OnTouchListener() {
			// @Override
			// public boolean onTouch(View view, MotionEvent event) {
			// // System.out.println("xxxxx");
			// // System.out.println(event.getAction() ==
			// // MotionEvent.ACTION_UP);
			// if (event.getAction() == MotionEvent.ACTION_UP
			// || event.getAction() == MotionEvent.ACTION_CANCEL) {
			// view.setBackgroundDrawable(null);
			// } else {
			// view.setBackgroundResource(ACCBaseAdapter.this.defaultTouchColor
			// // R.color.gray
			// );
			// }
			// return false;
			// }
			// });
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (ACCBaseAdapter.this.onItemClickListener != null
							&& !ListUtil.isEmpty(values)) {
						ACCBaseAdapter.this.onItemClickListener
								.onItemClick(ACCBaseAdapter.this.values
										.get((Integer) v.getTag(// R.id.tag_adapter_key
												// ResourceUtil.getId(context,
												// "tag_adapter_key")
												tagId)));
						// System.out.println(position);
					}
				}
			});
			// holderObject = this.getViewHolder(convertView);
			// convertView.setTag(holderObject);
			// convertView.setTag(k);
		}
		convertView.setTag(
		// R.id.tag_adapter_key
		// ResourceUtil.getId(context, "tag_adapter_key")
		// this.tagKey
				tagId, position);
		// else {
		// holderObject = convertView.getTag();
		// }
		// T t = this.values.get(position);
		// this.updateViewHolder(holderObject, this.values.get(position));
		this.updateView(convertView, this.getItem(position));
		// viewHolder.photoImageView.setImageResource(R.drawable.email1);
		// // viewHolder.stateImageView.setImageResource(Integer.valueOf(String
		// // .valueOf(map.get("StateImage"))));
		// viewHolder.descriptionTextView.setText(task.getDescription()
		// // String.valueOf(map
		// // .get("Item_taskType"))
		// );
		// viewHolder.stateTextView.setText("浠诲姟鐘舵�侊細"
		// + TaskUtil.getTaskStatusString(task.getTaskStatus())
		// // String.valueOf(map
		// // .get("Item_upstatus"))
		// );
		// viewHolder.timeTextView.setText(TimeUtil.dateToAppString(task
		// .getTaskTime())
		// // String.valueOf(map.get("Item_taskTime"))
		// );
		// if (task.getIsPass() == DatabaseRelateConstant.TASK_ISPASS_NO
		// // && !StringUtil.isEmpty(task.getDealContent())
		// ) {
		// viewHolder.sentBackView.setVisibility(View.VISIBLE);
		// viewHolder.rebackTextView.setText(StringUtil.getNotNullString(
		// task.getDealContent(), ""));
		// }
		// convertView.setBackgroundResource(R.drawable.bg_task_list_item);
		// convertView.setTag(key, tag);
		// Map<String, Object> map = attentionList.get(position + index
		// * VIEW_COUNT);
		// viewHolder.tv.setText(String.valueOf(map.get("Item_taskNO")));
		// imageLoader.DisplayImage(record.getItemImage(), activity,
		// viewHolder.iv);
		// imageManage2.displayImage(viewHolder.iv, record.getItemImage(),
		// R.drawable.image_indicator, 100, 100);
		return convertView;
	}

	public void clear() {
		if (!ListUtil.isEmpty(this.values)) {
			this.values.clear();
		}
	}

	public void add(List<T> ts) {
		if (!ListUtil.isEmpty(ts)) {
			this.values.addAll(ts);
			this.update();
		}
	}

	public void update() {
		this.notifyDataSetChanged();
	}

	public void add(T t) {
		this.add(t, true);
	}

	public void addAtBegin(T t) {
		this.add(t, false);
	}

	private void add(T t, boolean isEnd) {
		if (t != null) {
			if (isEnd) {
				this.values.add(t);
			} else {
				this.values.add(0, t);
			}
			this.update();
		}
	}
}