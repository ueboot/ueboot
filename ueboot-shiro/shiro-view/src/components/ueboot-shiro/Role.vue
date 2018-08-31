<template>
  <div>
   <!-- <Row>
      <i-col :span="6">
        <Row>
          <Button icon="md-refresh" type="primary" @click="fetchTreeData">刷新</Button>
          <Button icon="md-refresh" type="primary">管理角色</Button>
        </Row>
        <Row style="margin-top:20px">
          <u-tree :tree="tree" @item-click="itemClick"></u-tree>
          <Spin v-if="loadingTree">
            <Icon type="ios-loading" size=18 class="demo-spin-icon-load"></Icon>
            <div>加载中</div>
          </Spin>
        </Row>
      </i-col>
      <i-col :span="18">
      </i-col>
    </Row>-->
    <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>

  </div>
</template>

<script>
export default {
  name: 'Role',
  data () {
    return {
      loadingTree: false,
      tree: [],
      formGrid: {
        options: {
          url: {
            page: '/ueboot/role/page',
            save: '/ueboot/role/save',
            delete: '/ueboot/role/delete'
          }
        },
        toolbar: {
          superFilter: {
            columns: [ {type: 'hidden', name: 'roleId', init: null} ]
          }
        },
        form: {
          modal: {
            title: '角色管理'
          },
          columns: [
            {label: '角色名称', type: 'text', name: 'name', required: true},
            {label: '角色描述', type: 'text', name: 'description'},
            {label: '是否启用', type: 'select', name: 'available', required: true,
              data: [{'name': '启用', 'value': 'true'}, {'name': '不启用', 'value': 'false'}],
              init: 'true'
            }

          ]
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            {title: 'id', key: 'id',minWidth:60},
            {title: '角色名称', key: 'name',minWidth:160},
            {title: '角色描述', key: 'description',minWidth:100},
            {title: '是否启用', key: 'available',
              minWidth: 120,
              fieldFormat: (value, row) => {
                if (value) {
                  return '是'
                } else {
                  return {value: '否', cellClassName: 'table-cell-red'}
                }
              }}
          ]
        }
      }
    }
  },
  methods: {

    itemClick (oriNode, oriItem, e) {
      // 更改grid查询条件
      this.$set(this.formGrid.toolbar.superFilter.columns[0], 'init', oriItem.id)
      this.$refs.formGrid.$emit('reloadData')
    },
  }
}
</script>
