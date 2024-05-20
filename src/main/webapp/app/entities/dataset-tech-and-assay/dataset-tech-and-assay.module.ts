import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetTechAndAssayComponent } from './dataset-tech-and-assay.component';
import { DatasetTechAndAssayDetailComponent } from './dataset-tech-and-assay-detail.component';
import { DatasetTechAndAssayUpdateComponent } from './dataset-tech-and-assay-update.component';
import {
  DatasetTechAndAssayDeletePopupComponent,
  DatasetTechAndAssayDeleteDialogComponent,
} from './dataset-tech-and-assay-delete-dialog.component';
import { datasetTechAndAssayRoute, datasetTechAndAssayPopupRoute } from './dataset-tech-and-assay.route';

const ENTITY_STATES = [...datasetTechAndAssayRoute, ...datasetTechAndAssayPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetTechAndAssayComponent,
    DatasetTechAndAssayDetailComponent,
    DatasetTechAndAssayUpdateComponent,
    DatasetTechAndAssayDeleteDialogComponent,
    DatasetTechAndAssayDeletePopupComponent,
  ],
  entryComponents: [DatasetTechAndAssayDeleteDialogComponent],
})
export class IngestionApiDatasetTechAndAssayModule {}
