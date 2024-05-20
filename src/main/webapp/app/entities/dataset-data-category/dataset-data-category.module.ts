import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetDataCategoryComponent } from './dataset-data-category.component';
import { DatasetDataCategoryDetailComponent } from './dataset-data-category-detail.component';
import { DatasetDataCategoryUpdateComponent } from './dataset-data-category-update.component';
import {
  DatasetDataCategoryDeletePopupComponent,
  DatasetDataCategoryDeleteDialogComponent,
} from './dataset-data-category-delete-dialog.component';
import { datasetDataCategoryRoute, datasetDataCategoryPopupRoute } from './dataset-data-category.route';

const ENTITY_STATES = [...datasetDataCategoryRoute, ...datasetDataCategoryPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetDataCategoryComponent,
    DatasetDataCategoryDetailComponent,
    DatasetDataCategoryUpdateComponent,
    DatasetDataCategoryDeleteDialogComponent,
    DatasetDataCategoryDeletePopupComponent,
  ],
  entryComponents: [DatasetDataCategoryDeleteDialogComponent],
})
export class IngestionApiDatasetDataCategoryModule {}
