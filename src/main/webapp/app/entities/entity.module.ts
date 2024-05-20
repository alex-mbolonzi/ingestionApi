import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'application-reference-table',
        loadChildren: () =>
          import('./application-reference-table/application-reference-table.module').then(
            m => m.IngestionApiApplicationReferenceTableModule,
          ),
      },
      {
        path: 'dataset-data-category',
        loadChildren: () =>
          import('./dataset-data-category/dataset-data-category.module').then(m => m.IngestionApiDatasetDataCategoryModule),
      },
      {
        path: 'dataset-details',
        loadChildren: () => import('./dataset-details/dataset-details.module').then(m => m.IngestionApiDatasetDetailsModule),
      },
      {
        path: 'dataset-indication',
        loadChildren: () => import('./dataset-indication/dataset-indication.module').then(m => m.IngestionApiDatasetIndicationModule),
      },
      {
        path: 'dataset-role-details',
        loadChildren: () => import('./dataset-role-details/dataset-role-details.module').then(m => m.IngestionApiDatasetRoleDetailsModule),
      },
      {
        path: 'dataset-study',
        loadChildren: () => import('./dataset-study/dataset-study.module').then(m => m.IngestionApiDatasetStudyModule),
      },
      {
        path: 'dataset-tech-and-assay',
        loadChildren: () =>
          import('./dataset-tech-and-assay/dataset-tech-and-assay.module').then(m => m.IngestionApiDatasetTechAndAssayModule),
      },
      {
        path: 'dataset-therapy',
        loadChildren: () => import('./dataset-therapy/dataset-therapy.module').then(m => m.IngestionApiDatasetTherapyModule),
      },
      {
        path: 'dataset-user-usage-restriction',
        loadChildren: () =>
          import('./dataset-user-usage-restriction/dataset-user-usage-restriction.module').then(
            m => m.IngestionApiDatasetUserUsageRestrictionModule,
          ),
      },
      {
        path: 'email-template',
        loadChildren: () => import('./email-template/email-template.module').then(m => m.IngestionApiEmailTemplateModule),
      },
      {
        path: 'ingestion-request-details',
        loadChildren: () =>
          import('./ingestion-request-details/ingestion-request-details.module').then(m => m.IngestionApiIngestionRequestDetailsModule),
      },
      {
        path: 'request-status-details',
        loadChildren: () =>
          import('./request-status-details/request-status-details.module').then(m => m.IngestionApiRequestStatusDetailsModule),
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.IngestionApiStatusModule),
      },
      {
        path: 'technical-details',
        loadChildren: () => import('./technical-details/technical-details.module').then(m => m.IngestionApiTechnicalDetailsModule),
      },
      {
        path: 'validation-notes',
        loadChildren: () => import('./validation-notes/validation-notes.module').then(m => m.IngestionApiValidationNotesModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class IngestionApiEntityModule {}
