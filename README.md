# CustomProjectile
 커스텀 발사체 api입니다.
 
 구현된 기능
 --
 - 대부분의 엔티티 발사 가능
 - 시작 지점, 사거리, 방향, 속도 지정 가능
 - 파티클 사용/미사용 가능(기본값 미사용)
 - 어떤 파티클을 소환할지 지정 가능

사용법
--
```java
CustomProjectile projectile = new CustomProjectile(EntityType.BAT);

//파티클 사용 여부와 파티클을 설정합니다.
projectile.setUseParticle(true);
projectile.setParticle(Particle.SPELL_WITCH);

//데미지 사용 여부와 데미지를 설정합니다.
projectile.setUseDamage(true);
projectile.setDamage(10.0);

//설정한 발사체를 발사합니다. 인자로는 시작할 좌표, 사거리, 방향, 초당 속도를 받습니다.
projectile.launch(/* 시작할 좌표 */, 100, /* 방향 */, 20);

```
