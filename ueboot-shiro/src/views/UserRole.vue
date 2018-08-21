<template>
    <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
    export default {
        name: 'UserRole',
        data () {
            return {
                formGrid: {
                    options: {
                        version: 'v2',
                        url: {
                            page: "//ueboot/userRole/page",
                            save: "//ueboot/userRole/save",
                            delete: "//ueboot/userRole/delete",
                        }
                    },
                    tips: {title: "UserRole", content: "UserRole"},

                    form: {
                        modal: {
                            title: "UserRole"
                        },
                        columns: [
                                        {label: "id", type: "text", name: "id", required: true},
            
                        ]
                    },
                    table: {
                        operation: {
                            primaryKey: "id",
                        },
                        columns: [
                                                        { title: 'id', key: 'id' },
                                                    ]
                    }
                }
            }
        },
        methods: {

        }
    }
</script>
