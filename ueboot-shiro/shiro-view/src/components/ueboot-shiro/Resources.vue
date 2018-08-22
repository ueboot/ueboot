<template>
    <div>
      <Row>
        <i-col :span="6">
          <u-tree :tree="tree" @itemClick="itemClick"></u-tree>
        </i-col>
        <i-col :span="18">
          <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
        </i-col>
      </Row>
    </div>
</template>

<script>
  export default {
    name: 'Resources',
    data() {
      return {
        tree:[{id: 1, "name": "根节点", parentId: null}],
        formGrid: {
          options: {
            url: {
              page: "/ueboot/resources/page",
              save: "/ueboot/resources/save",
              delete: "/ueboot/resources/delete",
            }
          },
          form: {
            modal: {
              title: "Resources"
            },
            columns: [
              {label: "资源名称", type: "text", name: "name", required: true},
              {label: "资源类型", type: "text", name: "resourceType", required: true},
              {label: "资源路径", type: "text", name: "url", required: true},
              {label: "UI渲染", type: "text", name: "themeJson", required: true},
              {label: "权限标识符", type: "text", name: "permission", required: true},
              {label: "父级资源", type: "text", name: "parentCode", required: true},
              {label: "排序", type: "text", name: "rank", required: true},
              {label: "是否启用", type: "text", name: "available", required: true},

            ]
          },
          table: {
            operation: {
              primaryKey: "id",
            },
            columns: [
              {title: 'id', key: 'id'},
              {title: '资源名称', key: 'name'},
              {title: '资源类型', key: 'resourceType'},
              {title: '资源路径', key: 'url'},
              {title: 'UI渲染', key: 'themeJson'},
              {title: '权限标识符', key: 'permission'},
              {title: '父级资源', key: 'parentName'},
              {title: '排序', key: 'rank'},
              {title: '是否启用', key: 'available'},
            ]
          }
        }
      }
    },
    mounted:{
      initTree(){
      }
    },

    methods: {
      itemClick(){

      }
    }
  }
</script>
