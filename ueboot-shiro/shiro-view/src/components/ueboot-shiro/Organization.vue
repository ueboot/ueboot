<template>
    <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
export default {
  name: 'Organization',
  data () {
    return {
      formGrid: {
        options: {
          version: 'v2',
          url: {
            page: '/ueboot/organization/testpage',
            save: '/ueboot/organization/save',
            delete: '/ueboot/organization/delete'
          }
        },
        tips: {title: 'Organization', content: 'Organization'},

        form: {
          modal: {
            title: 'Organization'
          },
          columns: [
            {label: 'id', type: 'text', name: 'id', required: true},
            {label: 'code', type: 'text', name: 'code', required: true},
            {label: 'name', type: 'text', name: 'name', required: true},
            {label: 'address', type: 'text', name: 'address', required: true},
            {label: 'telephone', type: 'text', name: 'telephone', required: true},
            {label: 'type', type: 'text', name: 'type', required: true},
            {label: 'parentCode', type: 'text', name: 'parentCode', required: true},
            {label: 'parentPath', type: 'text', name: 'parentPath', required: true},
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
            { title: 'address', key: 'address' },
            { title: 'telephone', key: 'telephone' },
            { title: 'type', key: 'type' },
            { title: 'parentCode', key: 'parentCode' },
            { title: 'parentPath', key: 'parentPath' },
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
