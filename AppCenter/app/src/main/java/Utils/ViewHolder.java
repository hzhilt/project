package Utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by root on 14-12-18.
 */
public class ViewHolder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view,int id){
        SparseArray<View> viewHolder = (SparseArray<View>)view.getTag();
        if (viewHolder == null){
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if(childView == null){
            childView = view.findViewById(id);
            viewHolder.put(id,childView);
        }
        return (T)childView;
    }
}
