<template>
    <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
export default {
  name: 'Resources',
  data () {
    return {
      formGrid: {
        options: {
          version: 'v2',
          url: {
            page: '/platform/resources/page',
            save: '/platform/resources/save',
            delete: '/platform/resources/delete'
          }
        },
        tips: {title: 'Resources', content: 'Resources'},

        form: {
          modal: {
            title: 'Resources'
          },
          columns: [
            {label: 'id', type: 'text', name: 'id', required: true},
            {label: 'code', type: 'text', name: 'code', required: true},
            {label: 'name', type: 'text', name: 'name', required: true},
            {label: 'resourceType', type: 'text', name: 'resourceType', required: true},
            {label: 'url', type: 'text', name: 'url', required: true},
            {label: 'themeJson', type: 'text', name: 'themeJson', required: true},
            {label: 'permission', type: 'text', name: 'permission', required: true},
            {label: 'parentCode', type: 'text', name: 'parentCode', required: true},
            {label: 'parentPath', type: 'text', name: 'parentPath', required: true},
            {label: 'rank', type: 'text', name: 'rank', required: true},
            {label: 'level', type: 'text', name: 'level', required: true},
            {label: 'available', type: 'text', name: 'available', required: true}

          ]
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            { title: 'id', key: 'id' },
            { title: 'code', key: 'code' },
            { title: 'name', key: 'name' },
            { title: 'resourceType', key: 'resourceType' },
            { title: 'url', key: 'url' },
            { title: 'themeJson', key: 'themeJson' },
            { title: 'permission', key: 'permission' },
            { title: 'parentCode', key: 'parentCode' },
            { title: 'parentPath', key: 'parentPath' },
            { title: 'rank', key: 'rank' },
            { title: 'level', key: 'level' },
            { title: 'available', key: 'available' }
          ]
        }
      }
    }
  },
  methods: {

  }
}
</script>
