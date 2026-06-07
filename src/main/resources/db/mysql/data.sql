INSERT INTO `provinces` (`name`)
VALUES ('البرز'),
       ('اردبیل'),
       ('بوشهر'),
       ('چهارمحال و بختیاری'),
       ('آذربایجان شرقی'),
       ('اصفهان'),
       ('فارس'),
       ('گیلان'),
       ('گلستان'),
       ('همدان'),
       ('هرمزگان'),
       ('ایلام'),
       ('کرمان'),
       ('کرمانشاه'),
       ('خوزستان'),
       ('کهگیلویه و بویراحمد'),
       ('کردستان'),
       ('لرستان'),
       ('مرکزی'),
       ('مازندران'),
       ('خراسان شمالی'),
       ('قزوین'),
       ('قم'),
       ('خراسان رضوی'),
       ('سمنان'),
       ('سیستان و بلوچستان'),
       ('خراسان جنوبی'),
       ('تهران'),
       ('آذربایجان غربی'),
       ('یزد'),
       ('زنجان');

INSERT INTO `user_groups`
    (`name`, `description`)
VALUES ('Admin', 'Administrators with full access to the system.'),
       ('Customer', 'Normal customers with minimal access');

INSERT INTO `users`
(`first_name`, `last_name`, `email`, `phone`, `password`, `user_group_id`, `gender`, `description`)
VALUES ('طه',
        'حاجی وند',
        'hajivandtaha@gmail.com',
        '361629708',
        '$2a$10$Wi10.ZysHM.MF7l/n0/z7uyAdUQMrU8anj/5oAs4RrEinY6J22ZGO',
        1,
        'MALE',
        'A passionate tech enthusiast and entrepreneur.');

INSERT INTO `addresses`
(user_id, address_type, province_id, address_line, display_order, postal_code)
VALUES (1,
        'HOME',
        12,
        'New York',
        1,
        '234234');

INSERT INTO `shops` (`name`, `phone`, `address_id`, `status`, `description`)
VALUES ('دستسازه های سنگی آترا',
        '123-4567',
        1,
        'ACTIVE',
        'A family-owned bakery specializing in artisan breads and pastries since 1995. We use only the finest organic ingredients.');

INSERT INTO `shop_members` (`user_id`, `shop_id`, `description`)
VALUES (1, 1, 'owner of the shop');

INSERT INTO `service_groups` (`name`, `description`)
VALUES ('حمل و نقل', 'انواع روش‌های ارسال کالا (پست، حضوری، اسنپ)');

INSERT INTO `services` (`name`, `cost`, `service_group_id`, `description`)
VALUES ('پست', 50000.00, 1, 'ارسال از طریق شرکت پست جمهوری اسلامی ایران'),
       ('حضوری', 0.00, 1, 'تحویل حضوری توسط مشتری'),
       ('اسنپ', 70000.00, 1, 'ارسال از طریق پیک اسنپ');

INSERT INTO `order_statuses` (`name`)
VALUES ('PENDING'),
       ('DELIVERED'),
       ('RETURNED'),
       ('CANCELLED');

INSERT INTO `sms_statuses` (`code`, `status`)
VALUES (0, 'نام کاربری یا رمز عبور اشتباه می باشد.'),
       (1, 'ارسال موفقیت آمیز.'),
       (2, 'اعتبار کافی نمی باشد.'),
       (3, 'محدودیت در ارسال روزانه'),
       (4, 'محدودیت در حجم ارسال'),
       (5, 'شماره فرستنده معتبر نمی باشد.'),
       (6, 'سامانه در حال بروزرسانی می باشد.'),
       (7, 'متن حاوی کلمه فیلتر شده می باشد.'),
       (9, 'ارسال از خطوط عمومی از طریق وب سرویس امکان پذیر نمی باشد.'),
       (10, 'کاربر مورد نظر فعال نمی باشد.'),
       (11, 'ارسال نشده'),
       (12, 'مدارک کاربر کامل نمی باشد.'),
       (14, 'متن حاوی لینک می باشد.'),
       (15, 'ارسال به بیش از 1 شماره همراه بدون درج "لغو11" ممکن نیست.'),
       (16, 'شماره گیرنده ای یافت نشد'),
       (17, 'متن پیامک خالی می باشد'),
       (18, 'در صف ارسال می باشد.'),
       (19, 'خطای منطقی رخ داده است.'),
       (35, 'در REST به معنای وجود شماره در لیست سیاه مخابرات می‌باشد');

INSERT INTO `sms_templates` (`name`, `message`)
VALUES ('OTP',
        'کد ورود شما: {0} \n\n دستسازه‌های سنگی آترا');

INSERT INTO `categories` (name, url, icon, parent_id, display_order, description)
VALUES ('زیور آلات', 'accessory', NULL, NULL, 1, ''),
       ('دستبند', 'bracelet', NULL, NULL, 1, '');

INSERT INTO `attributes` (name, category_id, type, is_filterable)
VALUES ('جنس سنگ', 2, 'TEXT', 0),
       ('نوع دریا', 2, 'TEXT', 0);

INSERT INTO `roles`(`name`, `description`)
VALUES ('SUPER_ADMIN', 'this role has all accesses to every panel and feature');

INSERT INTO `permissions`(`name`, `description`)
VALUES ('ALL_PERMISSIONS', 'only for admin with full access');

INSERT INTO `role_permissions`(`permission_id`, `role_id`)
VALUES (1, 1);

INSERT INTO `user_group_roles`(`role_id`, `user_group_id`)
VALUES (1, 1);

INSERT INTO `attribute_values` (`value`)
VALUES ('طلا'),
       ('نقره'),
       ('پلاتین'),
       ('استیل'),
       ('تیتانیوم'),
       ('چرم'),
       ('پلاستیک'),
       ('سیلیکون'),
       ('فلز'),
       ('الماس'),
       ('یاقوت'),
       ('زمرد'),
       ('یاقوت کبود'),
       ('مروارید'),
       ('عقیق');

INSERT INTO `attribute_values_map` (`attribute_id`, `attribute_value_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3);

INSERT INTO `media_types` (`name`, `description`)
VALUES ('IMAGE', 'Standard image file (JPEG, PNG, GIF)'),
       ('VIDEO', 'Video file (MP4, AVI, MOV)'),
       ('PDF', 'PDF document file'),
       ('AUDIO', 'Audio file (MP3, WAV)'),
       ('ICON', 'Small icon image used for UI elements'),
       ('THUMBNAIL', 'Thumbnail image for previews'),
       ('DOCUMENT', 'Other document types, e.g., DOCX, XLSX'),
       ('SVG', 'Vector graphic in SVG format');
