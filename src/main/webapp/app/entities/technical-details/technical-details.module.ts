import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { TechnicalDetailsComponent } from './technical-details.component';
import { TechnicalDetailsDetailComponent } from './technical-details-detail.component';
import { TechnicalDetailsUpdateComponent } from './technical-details-update.component';
import { TechnicalDetailsDeletePopupComponent, TechnicalDetailsDeleteDialogComponent } from './technical-details-delete-dialog.component';
import { technicalDetailsRoute, technicalDetailsPopupRoute } from './technical-details.route';

const ENTITY_STATES = [...technicalDetailsRoute, ...technicalDetailsPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TechnicalDetailsComponent,
    TechnicalDetailsDetailComponent,
    TechnicalDetailsUpdateComponent,
    TechnicalDetailsDeleteDialogComponent,
    TechnicalDetailsDeletePopupComponent,
  ],
  entryComponents: [TechnicalDetailsDeleteDialogComponent],
})
export class IngestionApiTechnicalDetailsModule {}
