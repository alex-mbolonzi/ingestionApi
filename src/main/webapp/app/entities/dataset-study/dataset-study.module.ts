import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetStudyComponent } from './dataset-study.component';
import { DatasetStudyDetailComponent } from './dataset-study-detail.component';
import { DatasetStudyUpdateComponent } from './dataset-study-update.component';
import { DatasetStudyDeletePopupComponent, DatasetStudyDeleteDialogComponent } from './dataset-study-delete-dialog.component';
import { datasetStudyRoute, datasetStudyPopupRoute } from './dataset-study.route';

const ENTITY_STATES = [...datasetStudyRoute, ...datasetStudyPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetStudyComponent,
    DatasetStudyDetailComponent,
    DatasetStudyUpdateComponent,
    DatasetStudyDeleteDialogComponent,
    DatasetStudyDeletePopupComponent,
  ],
  entryComponents: [DatasetStudyDeleteDialogComponent],
})
export class IngestionApiDatasetStudyModule {}
