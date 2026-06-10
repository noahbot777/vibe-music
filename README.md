# Vibe Music 🎵

一个现代化的音乐管理系统，包含用户客户端（Client）、管理后台（Admin）和后端服务（Server）。
**[主分支修改]：更新了登录逻辑，统一了前后端的token过期时间。**
## 📖 项目简介

Vibe Music 是一个基于前后端分离架构的音乐管理系统，提供音乐播放、歌单管理、用户管理等功能。

**项目特色：**
- 🎶 完整的音乐播放功能
- 📱 响应式设计，支持多端访问
- 🔐 JWT 身份认证与权限管理
- ☁️ MinIO 对象存储支持
- 📊 可视化数据统计仪表盘

## 🏗️ 架构设计

```
┌─────────────────────────────────────────────────────────────────┐
│                        Vibe Music 系统架构                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   ┌──────────────┐      ┌──────────────┐                       │
│   │  Client      │      │   Admin      │                       │
│   │  (用户端)    │      │  (管理后台)  │                       │
│   │  Vue 3       │      │  Vue 3       │                       │
│   └──────┬───────┘      └──────┬───────┘                       │
│          │                     │                                │
│          ▼                     ▼                                │
│   ┌──────────────────────────────────────┐                      │
│   │            Server (后端服务)          │                      │
│   │        Spring Boot 3.3.7              │                      │
│   │   ┌─────────┐ ┌─────────┐ ┌─────────┐│                      │
│   │   │ Controller│ │ Service │ │  Mapper ││                      │
│   │   └────┬────┘ └────┬────┘ └────┬────┘│                      │
│   │        │           │           │      │                      │
│   └────────┼───────────┼───────────┼──────┘                      │
│            ▼           ▼           ▼                            │
│   ┌──────────────┐ ┌──────────┐ ┌──────────┐                   │
│   │   MySQL      │ │  Redis   │ │  MinIO   │                   │
│   │   (数据存储)  │ │ (缓存)   │ │ (文件存储)│                   │
│   └──────────────┘ └──────────┘ └──────────┘                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 🛠️ 技术栈

### 前端

| 模块 | 框架 | 版本 |
|------|------|------|
| 用户客户端 | Vue | 3.5.x |
| 管理后台 | Vue | 3.5.x |
| 构建工具 | Vite | 6.0.x |
| 语言 | TypeScript | 5.6.x |
| UI 组件 | Element Plus | 2.9.x |
| 状态管理 | Pinia | 2.3.x |
| 路由 | Vue Router | 4.5.x |
| 样式 | Tailwind CSS | 3.4.x |
| 图标 | Iconify | - |
| 图表 | ECharts | 5.5.x |

### 后端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 3.3.7 |
| ORM | MyBatis Plus | 3.5.9 |
| 数据库 | MySQL | 8.0+ |
| 缓存 | Redis | 6.0+ |
| 文件存储 | MinIO | 2023.01+ |
| 身份认证 | JWT | 4.4.0 |
| 连接池 | Druid | 1.2.18 |

## 📁 项目结构

```
vibe-music/                                    # 项目根目录
├── vibe-music-client/                         # 用户客户端
│   ├── src/
│   │   ├── components/                        # UI 组件
│   │   ├── hooks/                             # 组合式函数
│   │   │   └── useAudioPlayer.ts              # 音频播放器
│   │   ├── router/                            # 路由配置
│   │   ├── stores/                            # 状态管理
│   │   ├── utils/                             # 工具函数
│   │   │   └── http.ts                        # HTTP 请求封装
│   │   └── views/                             # 页面视图
│   ├── index.html
│   ├── package.json
│   └── vite.config.ts
├── vibe-music-admin/                          # 管理后台
│   ├── src/
│   │   ├── api/                               # API 接口
│   │   ├── components/                        # 公共组件
│   │   ├── layout/                            # 布局组件
│   │   ├── router/                            # 路由配置
│   │   ├── store/                             # Pinia 状态管理
│   │   ├── views/                             # 页面视图
│   │   │   ├── artist/                        # 歌手管理
│   │   │   ├── banner/                        # 轮播图管理
│   │   │   ├── feedback/                      # 反馈管理
│   │   │   ├── playlist/                      # 歌单管理
│   │   │   ├── song/                          # 歌曲管理
│   │   │   ├── user/                          # 用户管理
│   │   │   └── welcome/                       # 首页仪表盘
│   │   └── utils/                             # 工具函数
│   ├── index.html
│   ├── package.json
│   └── vite.config.ts
└── vibe-music-admin/vibe-music-server/        # 后端服务
    ├── server/                                # Spring Boot 后端
    │   └── src/main/java/cn/edu/seig/vibemusic/
    │       ├── controller/                    # REST API 控制器
    │       ├── service/                       # 业务逻辑层
    │       ├── mapper/                        # 数据访问层
    │       ├── model/                         # 数据模型
    │       │   ├── dto/                       # 请求/响应 DTO
    │       │   ├── entity/                    # 数据库实体
    │       │   └── vo/                        # 视图对象
    │       ├── config/                        # 配置类
    │       │   └── RolePermissionManager.java # 角色权限管理
    │       ├── interceptor/                   # 拦截器
    │       │   └── LoginInterceptor.java      # 登录拦截器
    │       └── VibeMusicServerApplication.java
    └── client/                                # 嵌套的客户端（备用）
```

## 🚀 快速开始

### 环境要求

| 依赖 | 版本 | 说明 |
|------|------|------|
| Node.js | >= 18.18.0 | 前端运行环境 |
| Java | >= 17 | 后端运行环境 |
| MySQL | >= 8.0 | 数据库 |
| Redis | >= 6.0 | 缓存服务 |
| MinIO | >= 2023.01 | 对象存储 |

### 1. 初始化环境

#### 1.1 创建数据库

```sql
CREATE DATABASE vibe_music CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 1.2 配置后端

修改 `vibe-music-admin/vibe-music-server/server/src/main/resources/application.yml`：

```yaml
server:
  port: 8081
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/vibe_music?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      password: MyStrongPass123

minio:
  endpoint: http://127.0.0.1:9001
  accessKey: minioadmin
  secretKey: minioadmin
  bucketName: vibe-music-data
```

#### 1.3 启动 Redis

```bash
redis-server
```

#### 1.4 启动 MinIO

```bash
minio server "D:\GitHub开源项目\minio\data" --console-address ":9090" --address "127.0.0.1:9001"
```

创建存储桶：

```bash
mc mb local/vibe-music-data
mc anonymous set download local/vibe-music-data
```

### 2. 启动后端服务

```bash
cd vibe-music-admin/vibe-music-server/server
mvn spring-boot:run
```

后端服务将在 `http://localhost:8081/api` 启动。

### 3. 启动用户客户端

```bash
cd vibe-music-client
npm install
npm run dev
```

客户端将在 `http://localhost:8092` 启动。

### 4. 启动管理后台

```bash
cd vibe-music-admin
npm install
npm run dev
```

管理后台将在 `http://localhost:20003` 启动。

## 🔐 默认账号

| 角色 | 用户名 | 密码 | 邮箱 |
|------|--------|------|------|
| 管理员 | admin_1 | 123456abc | admin_1@example.com |
| 普通用户 | user_1 | 123456abc | user_1@example.com |

## 📋 API 接口

### 公开接口（无需登录）

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/banner/getBannerList` | 获取轮播图列表 |
| GET | `/api/playlist/getAllPlaylists` | 获取歌单列表 |
| GET | `/api/playlist/getRecommendedPlaylists` | 获取推荐歌单 |
| GET | `/api/playlist/getPlaylistDetail/{id}` | 获取歌单详情 |
| GET | `/api/artist/getAllArtists` | 获取歌手列表 |
| GET | `/api/artist/getArtistDetail/{id}` | 获取歌手详情 |
| GET | `/api/song/getAllSongs` | 获取歌曲列表 |
| GET | `/api/song/getRecommendedSongs` | 获取推荐歌曲 |
| GET | `/api/song/getSongDetail/{id}` | 获取歌曲详情 |
| GET | `/api/song/url/v1` | 获取歌曲播放地址 |
| POST | `/api/user/login` | 用户登录 |
| POST | `/api/user/register` | 用户注册 |
| POST | `/api/user/sendVerificationCode` | 发送验证码 |

### 用户接口（需要登录）

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/user/getUserInfo` | 获取用户信息 |
| POST | `/api/user/logout` | 用户登出 |
| GET | `/api/favorite/getFavorites` | 获取收藏列表 |
| POST | `/api/favorite/addFavorite` | 添加收藏 |
| DELETE | `/api/favorite/removeFavorite` | 删除收藏 |

### 管理员接口（需要管理员权限）

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/admin/getAllSongsCount` | 获取歌曲统计 |
| POST | `/api/admin/addSong` | 添加歌曲 |
| POST | `/api/admin/updateSong` | 更新歌曲 |
| DELETE | `/api/admin/deleteSong/{id}` | 删除歌曲 |
| POST | `/api/admin/addArtist` | 添加歌手 |
| POST | `/api/admin/updateArtist` | 更新歌手 |
| DELETE | `/api/admin/deleteArtist/{id}` | 删除歌手 |
| POST | `/api/admin/addPlaylist` | 添加歌单 |
| POST | `/api/admin/updatePlaylist` | 更新歌单 |
| DELETE | `/api/admin/deletePlaylist/{id}` | 删除歌单 |
| POST | `/api/admin/addBanner` | 添加轮播图 |
| POST | `/api/admin/updateBanner` | 更新轮播图 |
| DELETE | `/api/admin/deleteBanner/{id}` | 删除轮播图 |
| GET | `/api/admin/getAllUsers` | 获取用户列表 |
| POST | `/api/admin/updateUserStatus` | 更新用户状态 |
| DELETE | `/api/admin/deleteUser/{id}` | 删除用户 |

## 🔑 权限系统

### 角色定义

| 角色 | 标识 | 权限范围 |
|------|------|----------|
| 管理员 | `ROLE_ADMIN` | 访问所有接口，包括 `/api/admin/*` |
| 普通用户 | `ROLE_USER` | 访问 `/api/user/*`, `/api/playlist/*`, `/api/song/*`, `/api/favorite/*` 等 |

### 权限配置

权限配置位于 `application.yml`：

```yaml
role-path-permissions:
  permissions:
    ROLE_ADMIN:
      - "/api/admin/"
    ROLE_USER:
      - "/api/user/"
      - "/api/playlist/"
      - "/api/artist/"
      - "/api/song/"
      - "/api/favorite/"
      - "/api/comment/"
      - "/api/banner/"
      - "/api/feedback/"
```

## 📝 开发指南

### 代码规范

- **ESLint**: JavaScript/TypeScript 代码检查
- **Prettier**: 代码格式化
- **Stylelint**: CSS/SCSS 样式检查
- **Commitlint**: Git 提交规范

### 常用命令

**前端通用**

```bash
# 开发模式
npm run dev

# 构建生产版本
npm run build

# 代码检查
npm run lint

# 类型检查
npm run typecheck

# 预览构建结果
npm run preview
```

**后端**

```bash
# 启动开发服务器
mvn spring-boot:run

# 打包
mvn clean package

# 运行打包后的 Jar
java -jar target/vibe-music-server-0.0.1-SNAPSHOT.jar
```

## 🐛 常见问题

### Q: 前端请求报 403 Forbidden？

**原因分析**：
- Token 未正确携带或已过期
- 用户角色权限不足
- 权限配置路径错误

**解决方案**：
1. 检查浏览器 LocalStorage 中是否存在 token
2. 检查 token 是否过期（有效期 6 小时已延期至一个月）
3. 确认用户角色是否有权限访问该接口

### Q: 无法访问 MinIO 存储的文件？

**原因分析**：
- MinIO 服务未启动或端口配置错误
- 存储桶权限未正确设置
- 文件 URL 端口不匹配

**解决方案**：
1. 确认 MinIO 运行在 `127.0.0.1:9001`
2. 执行 `mc anonymous set download local/vibe-music-data` 设置公开权限
3. 检查数据库中存储的文件 URL 是否使用正确端口

### Q: 音频播放失败？

**原因分析**：
- 音频文件 URL 无效或路径错误
- MinIO 服务未正常运行
- 浏览器不支持该音频格式

**解决方案**：
1. 确认 MinIO 服务正常运行
2. 直接在浏览器访问音频 URL 测试
3. 确保音频格式为 MP3、OGG 等浏览器支持的格式

### Q: 数据库连接失败？

**原因分析**：
- MySQL 服务未启动
- 数据库配置信息错误
- MySQL 8.0+ 需要配置 `allowPublicKeyRetrieval=true`

**解决方案**：
1. 确认 MySQL 服务运行在 `localhost:3306`
2. 检查数据库用户名和密码
3. 确保 JDBC URL 包含 `allowPublicKeyRetrieval=true` 参数

## 📊 功能模块

### 用户客户端功能

- 🎶 音乐播放（播放、暂停、上一首、下一首）
- 📋 歌单浏览与收藏
- 👤 用户登录与注册
- ❤️ 收藏管理
- 💬 评论功能

### 管理后台功能

- 📊 数据统计仪表盘
- 🎵 歌曲管理（增删改查、批量操作）
- 👨‍🎤 歌手管理
- 📝 歌单管理
- 🖼️ 轮播图管理
- 👥 用户管理
- 💬 反馈管理

## 📄 许可证

MIT License

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📧 联系方式

如有问题或建议，请通过以下方式联系：

- 邮箱：3552139488@qq.com
- GitHub：[Vibe Music](https://github.com/noahbot777/vibe-music)

---

**🎉 感谢使用 Vibe Music！**