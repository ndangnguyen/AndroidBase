package ndn.base.nguyen.base.recyclerView

import ndn.base.nguyen.utils.Constants.POSITION_DEFAULT

/**
 * OnItemClickListener
 *
 * @param <T> Data from item click
</T> */

interface OnItemClickListener<T> {
  fun onItemViewClick(item: T, position: Int = POSITION_DEFAULT)
}