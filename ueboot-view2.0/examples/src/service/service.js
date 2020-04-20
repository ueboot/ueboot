// import treeData from '../assets/dataowner_json'
let TREE_CACHE = null

export default {
    queryTreeData(vue) {
        if (TREE_CACHE !== null) {
            vue.$log.d("从缓存读取")
            return TREE_CACHE
        } else {
            vue.$log.d("调用接口")
            // TREE_CACHE = treeData.tree
            return TREE_CACHE
        }
    }
}
