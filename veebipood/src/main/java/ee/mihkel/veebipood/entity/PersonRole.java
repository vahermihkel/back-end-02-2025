package ee.mihkel.veebipood.entity;

// user - saab tellida, profiili vaadata, tellimusi vaadata
// admin - saab tooteid/kategooriaid lisada/kustutada/muuta
// superadmin - saab kõike mis admin, aga lisaks ka admin'e lisada ja eemaldada

public enum PersonRole {
    USER, ADMIN, SUPERADMIN
}
