//package com.news.update.component;
//
//import com.news.update.entity.Admins;
//import com.news.update.entity.Category;
//import com.news.update.entity.Role;
//import com.news.update.entity.Tags;
//import com.news.update.repository.AdminsRepository;
//import com.news.update.repository.CategoryRepository;
//import com.news.update.repository.RoleRepository;
//import com.news.update.repository.TagsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private AdminsRepository adminsRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private TagsRepository tagsRepository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            Role roleAdmin = new Role();
//            roleAdmin.setName("ROLE_ADMIN");
//            Admins admins = new Admins();
//            admins.setFullname("Orifjon Yunusjonov");
//            admins.setUsername("admin");
//            admins.setPassword(
//                    passwordEncoder.encode(
//                            "root"
//                    )
//            );
//            admins.setPhone("+998932099924");
//            admins.setSocial("t.me/yunusjanoff");
//            roleRepository.save(roleAdmin);
//            admins.setRoles(roleRepository.findAll());
//            adminsRepository.save(admins);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            Category category = new Category();
//            String array[] = new String[]{"Jamiyat", "Iqtisod", "Dunyo", "Biznes", "Ijtimoiy", "Sayohat", "Salomatlik", "Moda", "Avto", "Sport", "Texnologiya", "Internet"};
//
//            for (int i = 0; i < array.length; i++) {
//                category = new Category();
//                category.setName(array[i]);
//                categoryRepository.save(category);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            Tags tags = new Tags();
//            String array1[] = new String[]{"Dizayn", "Iqtisod", "Dunyo", "Biznes", "Ijtimoiy", "Sayohat", "Salomatlik", "Moda", "Avto", "Sport", "Texnologiya", "Internet",
//                    "Fotografiya", "Qiziqarli"
//            };
//
//            for (int i = 0; i < array1.length; i++) {
//                tags = new Tags();
//                tags.setTag(array1[i]);
//                tagsRepository.save(tags);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//}
