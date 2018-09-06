let TREE_ID="UTREE_SELECT_ID"
export default {
  getId(){
    let id = window.sessionStorage.getItem(TREE_ID)||0
    id+=1
    window.sessionStorage.setItem(TREE_ID,id)
    return id;
  }
}
