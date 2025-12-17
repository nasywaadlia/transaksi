package ui.ft.ccit.faculty.transaksi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OpenApiConfig {

        /*
         * ======================================================
         * GLOBAL METADATA
         * ======================================================
         */
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("API Sistem Transaksi")
                                                .version("1.0.0")
                                                .description("""
                                                                Dokumentasi API internal Sistem Transaksi.

                                                                Catatan desain:
                                                                - API berbasis REST
                                                                - Pagination opsional
                                                                - Bulk operation bersifat transactional
                                                                - Swagger hanya sebagai dokumentasi manusia
                                                                """)
                                                .contact(new Contact()
                                                                .name("Muhammad Azka Ramadhan")
                                                                .email("m.azka@eng.ui.ac.id")));
        }

        /*
         * ======================================================
         * GROUP: BARANG
         * ======================================================
         */
        @Bean
        public GroupedOpenApi barangApi() {
                return GroupedOpenApi.builder()
                                .group("Barang")
                                .pathsToMatch("/api/barang/**")
                                .addOpenApiCustomizer(barangCustomizer())
                                .build();
        }

        /*
         * ======================================================
         * CUSTOMIZER
         * ======================================================
         */
        private OpenApiCustomizer barangCustomizer() {
                return openAPI -> {
                        if (openAPI.getPaths() == null)
                                return;

                        openAPI.getPaths().forEach((path, item) -> {
                                normalize(path, item.getGet(), "GET");
                                normalize(path, item.getPost(), "POST");
                                normalize(path, item.getPut(), "PUT");
                                normalize(path, item.getDelete(), "DELETE");
                        });
                };
        }

        /*
         * ======================================================
         * ENRICHMENT CORE
         * ======================================================
         */
        private void normalize(String rawPath, Operation op, String method) {
                if (op == null)
                        return;
                if (alreadyDocumented(op))
                        return;

                String path = normalizePath(rawPath);
                EndpointDoc doc = EndpointDoc.match(method, path);

                if (doc == null)
                        return;

                op.setSummary(doc.summary());
                op.setDescription(doc.description());
        }

        private boolean alreadyDocumented(Operation op) {
                return StringUtils.hasText(op.getSummary())
                                || StringUtils.hasText(op.getDescription());
        }

        private String normalizePath(String path) {
                return path.endsWith("/") && path.length() > 1
                                ? path.substring(0, path.length() - 1)
                                : path;
        }

        /*
         * ======================================================
         * DOCUMENTATION REGISTRY (NO IF-ELSE!)
         * ======================================================
         */
        enum EndpointDoc {

                GET_ALL(
                                "GET",
                                "/api/barang",
                                "Mengambil daftar semua barang",
                                """
                                                Mengambil seluruh data barang yang tersedia di sistem.
                                                Mendukung pagination opsional melalui parameter `page` dan `size`.
                                                """),

                GET_ONE(
                                "GET",
                                "/api/barang/{id}",
                                "Mengambil detail satu barang",
                                "Mengambil detail satu barang berdasarkan ID."),

                SEARCH(
                                "GET",
                                "/api/barang/search",
                                "Mencari barang berdasarkan nama",
                                "Mencari barang berdasarkan kata kunci pada nama."),

                CREATE(
                                "POST",
                                "/api/barang",
                                "Membuat barang baru",
                                "Membuat satu data barang baru ke dalam sistem."),

                BULK_CREATE(
                                "POST",
                                "/api/barang/bulk",
                                "Membuat barang secara bulk",
                                "Membuat banyak barang baru dalam satu transaksi."),

                UPDATE(
                                "PUT",
                                "/api/barang/{id}",
                                "Memperbarui data barang",
                                "Memperbarui data barang berdasarkan ID."),

                DELETE(
                                "DELETE",
                                "/api/barang/{id}",
                                "Menghapus barang",
                                "Menghapus satu barang berdasarkan ID."),

                BULK_DELETE(
                                "DELETE",
                                "/api/barang/bulk",
                                "Menghapus barang secara bulk",
                                "Menghapus banyak barang berdasarkan daftar ID.");

                private final String method;
                private final String path;
                private final String summary;
                private final String description;

                EndpointDoc(String method, String path, String summary, String description) {
                        this.method = method;
                        this.path = path;
                        this.summary = summary;
                        this.description = description;
                }

                public String summary() {
                        return summary;
                }

                public String description() {
                        return description;
                }

                static EndpointDoc match(String method, String path) {
                        for (EndpointDoc doc : values()) {
                                if (doc.method.equals(method) && doc.path.equals(path)) {
                                        return doc;
                                }
                        }
                        return null;
                }
        }
}
